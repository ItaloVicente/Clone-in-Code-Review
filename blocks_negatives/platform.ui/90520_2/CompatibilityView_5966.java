/*******************************************************************************
 * Copyright (c) 2010, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Steven Spungin <steven@spungin.tv> - Bug 436908
 *     Andrey Loskutov <loskutov@gmx.de> - Bug 372799, 446864
 *     Snjezana Peco <snjezana.peco@redhat.com> - Bug 414888
 *     Lars Vogel <Lars.Vogel@vogella.com> - Bug 503379
 ******************************************************************************/

package org.eclipse.ui.internal.e4.compatibility;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.di.PersistState;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.IPostSelectionProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPart2;
import org.eclipse.ui.IWorkbenchPartConstants;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.internal.ErrorEditorPart;
import org.eclipse.ui.internal.ErrorViewPart;
import org.eclipse.ui.internal.IWorkbenchConstants;
import org.eclipse.ui.internal.PartSite;
import org.eclipse.ui.internal.SaveableHelper;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.internal.WorkbenchPartReference;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.part.IWorkbenchPartOrientation;
import org.osgi.service.event.EventHandler;

public abstract class CompatibilityPart implements ISelectionChangedListener {




	@Inject
	Composite composite;

	@Inject
	Logger logger;

	IWorkbenchPart wrapped;

	MPart part;

	@Inject
	private IEventBroker eventBroker;

	private boolean beingDisposed = false;

	private boolean alreadyDisposed = false;

	/**
	 * This handler will be notified when the part's widget has been un/set.
	 */
	private EventHandler widgetSetHandler = event -> {
		if (event.getProperty(UIEvents.EventTags.ELEMENT) == part
				&& event.getProperty(UIEvents.EventTags.NEW_VALUE) == null) {
			 Assert.isTrue(!composite.isDisposed(),
			beingDisposed = true;
			WorkbenchPartReference reference = getReference();
			((WorkbenchPage) reference.getPage()).firePartDeactivatedIfActive(part);
			((WorkbenchPage) reference.getPage()).firePartHidden(part);
			((WorkbenchPage) reference.getPage()).firePartClosed(CompatibilityPart.this);
		}
	};

	/**
	 * This handler will be notified when the part's client object has been
	 * un/set.
	 */
	private EventHandler objectSetHandler = event -> {
		if (event.getProperty(UIEvents.EventTags.ELEMENT) == part
				&& event.getProperty(UIEvents.EventTags.NEW_VALUE) != null) {
			WorkbenchPartReference reference = getReference();
			((WorkbenchPage) reference.getPage()).firePartOpened(CompatibilityPart.this);
		}
	};

	private ISelectionChangedListener postListener = e -> {
		ESelectionService selectionService = part.getContext().get(ESelectionService.class);
		selectionService.setPostSelection(e.getSelection());
	};

	private IWorkbenchPart legacyPart;

	CompatibilityPart(MPart part) {
		this.part = part;
	}

	@PersistState
	void persistState() {
		ContextInjectionFactory.invoke(wrapped, PersistState.class, part.getContext(), null);
	}

	public abstract WorkbenchPartReference getReference();

	protected boolean createPartControl(final IWorkbenchPart legacyPart, Composite parent) {
		this.legacyPart = legacyPart;
		IWorkbenchPartSite site = null;
		try {
			site = legacyPart.getSite();
			part.getContext().set(Composite.class, parent);
			if (part.getTags().contains(IWorkbenchConstants.TAG_USE_DEPENDENCY_INJECTION)) {
				ContextInjectionFactory.inject(legacyPart, part.getContext());
			}
			legacyPart.createPartControl(parent);

		} catch (RuntimeException e) {
			logger.error(e);

			try {
				legacyPart.dispose();
			} catch (Exception ex) {
				logger.error(ex);
			}

			internalDisposeSite(site);

			IStatus status = new Status(IStatus.ERROR, WorkbenchPlugin.PI_WORKBENCH,
					"Failed to create the part's controls", e); //$NON-NLS-1$
			WorkbenchPartReference reference = getReference();
			wrapped = reference.createErrorPart(status);
			try {
				reference.initialize(wrapped);
				wrapped.createPartControl(parent);
			} catch (RuntimeException ex) {
				logger.error(ex);
			} catch (PartInitException ex) {
				WorkbenchPlugin.log("Unable to initialize error part", ex.getStatus()); //$NON-NLS-1$
			}
		}

		if (site != null) {
			ISelectionProvider selectionProvider = site.getSelectionProvider();
			if (selectionProvider != null) {
				selectionProvider.addSelectionChangedListener(this);

				if (selectionProvider instanceof IPostSelectionProvider) {
					((IPostSelectionProvider) selectionProvider)
							.addPostSelectionChangedListener(postListener);
				} else {
					selectionProvider.addSelectionChangedListener(postListener);
				}
				ESelectionService selectionService = part.getContext().get(ESelectionService.class);
				selectionService.setSelection(selectionProvider.getSelection());
			}
		}
		return true;
	}

	@Focus
	void delegateSetFocus() {
		try {
			if (part.getTags().contains(IWorkbenchConstants.TAG_USE_DEPENDENCY_INJECTION)) {
				ContextInjectionFactory.invoke(wrapped, Focus.class, part.getContext(), null);
			}
			wrapped.setFocus();
		} catch (Exception e) {
			if (logger != null) {
				msg += ' ' + part.getLocalizedLabel();
				logger.error(e, msg);
			}
		}
	}

	private void invalidate() {
		IWorkbenchPartSite site = null;
		if (wrapped != null) {
			site = wrapped.getSite();
			if (site != null) {
				ISelectionProvider selectionProvider = site.getSelectionProvider();
				if (selectionProvider != null) {
					selectionProvider.removeSelectionChangedListener(this);

					if (selectionProvider instanceof IPostSelectionProvider) {
						((IPostSelectionProvider) selectionProvider)
								.removePostSelectionChangedListener(postListener);
					} else {
						selectionProvider.removeSelectionChangedListener(postListener);
					}
				}
			}
		}

		WorkbenchPartReference reference = getReference();
		reference.invalidate();

		if (wrapped != null) {
			try {
				wrapped.dispose();
			} catch (Exception e) {
				logger.error(e);
			}
			wrapped = null;
		}

		internalDisposeSite(site);
		alreadyDisposed = true;
	}

	private String computeLabel() {
		if (wrapped instanceof IWorkbenchPart2) {
			String label = ((IWorkbenchPart2) wrapped).getPartName();
			return Util.safeString(label);
		}

		IWorkbenchPartSite site = wrapped.getSite();
		if (site != null) {
			return Util.safeString(site.getRegisteredName());
		}
		return Util.safeString(wrapped.getTitle());
	}

	/**
	 * Returns whether this part is being disposed. This is used for
	 * invalidating this part so that it is not returned when a method expects a
	 * "working" part.
	 * <p>
	 * See bug 308492.
	 * </p>
	 *
	 * @return if the part is currently being disposed
	 */
	public boolean isBeingDisposed() {
		return beingDisposed;
	}

	IWorkbenchPart createPart(WorkbenchPartReference reference) throws PartInitException {
		return reference.createPart();
	}

	boolean handlePartInitException(PartInitException e) {
		WorkbenchPartReference reference = getReference();
		IWorkbenchPartSite site = reference.getSite();
		reference.invalidate();
		if (wrapped instanceof IEditorPart) {
			try {
				wrapped.dispose();
			} catch (Exception ex) {
				logger.error(ex);
			}
		}
		internalDisposeSite(site);

		alreadyDisposed = false;
		WorkbenchPlugin.log("Unable to create part", e.getStatus()); //$NON-NLS-1$

		wrapped = reference.createErrorPart(e.getStatus());
		try {
			reference.initialize(wrapped);
		} catch (PartInitException ex) {
			WorkbenchPlugin.log("Unable to initialize error part", ex.getStatus()); //$NON-NLS-1$
			return false;
		}
		return true;
	}

	@PostConstruct
	public void create() {
		eventBroker.subscribe(UIEvents.UIElement.TOPIC_WIDGET, widgetSetHandler);
		eventBroker.subscribe(UIEvents.Contribution.TOPIC_OBJECT, objectSetHandler);

		WorkbenchPartReference reference = getReference();

		try {
			wrapped = createPart(reference);
			reference.initialize(wrapped);
		} catch (PartInitException e) {
			if (!handlePartInitException(e)) {
				return;
			}
		} catch (Exception e) {
			WorkbenchPlugin.log("Unable to initialize part", e); //$NON-NLS-1$
			if (!handlePartInitException(new PartInitException(e.getMessage()))) {
				return;
			}
		}


		int style = SWT.NONE;
		if (wrapped instanceof IWorkbenchPartOrientation) {
			style = ((IWorkbenchPartOrientation) wrapped).getOrientation();
		}

		Composite parent = new Composite(composite, style);
		parent.setLayout(new FillLayout());
		if (!createPartControl(wrapped, parent)) {
			return;
		}

		if (!(wrapped instanceof ErrorEditorPart) && !(wrapped instanceof ErrorViewPart)) {
			part.setLabel(computeLabel());
			part.getTransientData().put(IPresentationEngine.OVERRIDE_TITLE_TOOL_TIP_KEY, wrapped.getTitleToolTip());
			part.getTransientData().put(IPresentationEngine.OVERRIDE_ICON_IMAGE_KEY, wrapped.getTitleImage());
		}

		ISaveablePart saveable = SaveableHelper.getSaveable(wrapped);
		if (saveable != null && SaveableHelper.isDirtyStateSupported(wrapped)) {
			part.setDirty(saveable.isDirty());
		}

		wrapped.addPropertyListener((source, propId) -> {
			switch (propId) {
			case IWorkbenchPartConstants.PROP_TITLE:
				part.setLabel(computeLabel());

				if (wrapped.getTitleImage() != null) {
					Image newImage = wrapped.getTitleImage();
					part.getTransientData().put(IPresentationEngine.OVERRIDE_ICON_IMAGE_KEY,
							newImage);
				}
				String titleToolTip = wrapped.getTitleToolTip();
				if (titleToolTip != null) {
					part.getTransientData().put(IPresentationEngine.OVERRIDE_TITLE_TOOL_TIP_KEY, titleToolTip);
				}
				break;
			case IWorkbenchPartConstants.PROP_DIRTY:
				boolean supportsDirtyState = SaveableHelper.isDirtyStateSupported(wrapped);
				if (!supportsDirtyState) {
					part.setDirty(false);
					return;
				}
				ISaveablePart saveable1 = SaveableHelper.getSaveable(wrapped);
				if (saveable1 != null) {
					part.setDirty(saveable1.isDirty());
				} else if (part.isDirty()) {
					part.setDirty(false);
				}
				break;
			case IWorkbenchPartConstants.PROP_INPUT:
				WorkbenchPartReference ref = getReference();
				((WorkbenchPage) ref.getSite().getPage()).firePartInputChanged(ref);
				break;
			}
		});
	}

	@PreDestroy
	void destroy() {
		if (!alreadyDisposed) {
			invalidate();
		}

		eventBroker.unsubscribe(widgetSetHandler);
		eventBroker.unsubscribe(objectSetHandler);
	}

	/**
	 * Disposes of the 3.x part's site if it has one. Subclasses may override
	 * but must call <code>super.disposeSite()</code> in its implementation.
	 */
	private void internalDisposeSite(IWorkbenchPartSite site) {
		if (site instanceof PartSite) {
			disposeSite((PartSite) site);
		}
	}

	/**
	 * Disposes of the 3.x part's site if it has one. Subclasses may override
	 * but must call <code>super.disposeSite()</code> in its implementation.
	 */
	void disposeSite(PartSite site) {
		site.dispose();
		if (part.getTags().contains(IWorkbenchConstants.TAG_USE_DEPENDENCY_INJECTION)) {
			ContextInjectionFactory.uninject(legacyPart, part.getContext());
		}
	}

	@Persist
	void doSave() {
		ISaveablePart saveable = SaveableHelper.getSaveable(wrapped);
		if (saveable != null) {
			SaveableHelper.savePart(saveable, wrapped, getReference().getSite()
					.getWorkbenchWindow(), false);
		}
	}

	public IWorkbenchPart getPart() {
		return wrapped;
	}

	public MPart getModel() {
		return part;
	}

	@Override
	public void selectionChanged(SelectionChangedEvent e) {
		ESelectionService selectionService = part.getContext().get(ESelectionService.class);
		selectionService.setSelection(e.getSelection());
	}

	protected void clearMenuItems() {
		for (MMenu menu : part.getMenus()) {
			menu.getChildren().clear();
		}
	}
}
