
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
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
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
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPart2;
import org.eclipse.ui.IWorkbenchPartConstants;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.internal.ErrorEditorPart;
import org.eclipse.ui.internal.ErrorViewPart;
import org.eclipse.ui.internal.PartSite;
import org.eclipse.ui.internal.SaveableHelper;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.internal.WorkbenchPartReference;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.part.IWorkbenchPartOrientation;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public abstract class CompatibilityPart implements ISelectionChangedListener {

	public static final String COMPATIBILITY_EDITOR_URI = "bundleclass://org.eclipse.ui.workbench/org.eclipse.ui.internal.e4.compatibility.CompatibilityEditor"; //$NON-NLS-1$

	public static final String COMPATIBILITY_VIEW_URI = "bundleclass://org.eclipse.ui.workbench/org.eclipse.ui.internal.e4.compatibility.CompatibilityView"; //$NON-NLS-1$

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

	private EventHandler widgetSetHandler = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			if (event.getProperty(UIEvents.EventTags.ELEMENT) == part
					&& event.getProperty(UIEvents.EventTags.NEW_VALUE) == null) {
				 Assert.isTrue(!composite.isDisposed(),
										"The widget should not have been disposed at this point"); //$NON-NLS-1$
				beingDisposed = true;
				WorkbenchPartReference reference = getReference();
				((WorkbenchPage) reference.getPage()).firePartDeactivatedIfActive(part);
				((WorkbenchPage) reference.getPage()).firePartHidden(part);
				((WorkbenchPage) reference.getPage()).firePartClosed(CompatibilityPart.this);
			}
		}
	};

	private EventHandler objectSetHandler = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			if (event.getProperty(UIEvents.EventTags.ELEMENT) == part
					&& event.getProperty(UIEvents.EventTags.NEW_VALUE) != null) {
				WorkbenchPartReference reference = getReference();
				((WorkbenchPage) reference.getPage()).firePartOpened(CompatibilityPart.this);
			}
		}
	};

	private ISelectionChangedListener postListener = new ISelectionChangedListener() {

		@Override
		public void selectionChanged(SelectionChangedEvent e) {
			ESelectionService selectionService = (ESelectionService) part.getContext().get(
					ESelectionService.class.getName());
			selectionService.setPostSelection(e.getSelection());
		}
	};

	CompatibilityPart(MPart part) {
		this.part = part;
	}

	@PersistState
	void persistState() {
		ContextInjectionFactory.invoke(wrapped, PersistState.class, part.getContext(), null);
	}

	public abstract WorkbenchPartReference getReference();

	protected boolean createPartControl(final IWorkbenchPart legacyPart, Composite parent) {
		IWorkbenchPartSite site = null;
		try {
			site = legacyPart.getSite();
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
			}
		}
		return true;
	}

	@Focus
	void delegateSetFocus() {
		try {
			wrapped.setFocus();
		} catch (Exception e) {
			if (logger != null) {
				String msg = "Error setting focus to : " + part.getClass().getName(); //$NON-NLS-1$
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
			part.getTransientData().put(IPresentationEngine.OVERRIDE_TITLE_TOOL_TIP_KEY,
					wrapped.getTitleToolTip());
			part.getTransientData().put(IPresentationEngine.OVERRIDE_ICON_IMAGE_KEY,
					wrapped.getTitleImage());
		}

		if (wrapped instanceof ISaveablePart) {
			part.setDirty(((ISaveablePart) wrapped).isDirty());
		}

		wrapped.addPropertyListener(new IPropertyListener() {
			@Override
			public void propertyChanged(Object source, int propId) {
				switch (propId) {
				case IWorkbenchPartConstants.PROP_TITLE:
					part.setLabel(computeLabel());

					if (wrapped.getTitleImage() != null) {
						Image newImage = wrapped.getTitleImage();
						part.getTransientData().put(IPresentationEngine.OVERRIDE_ICON_IMAGE_KEY,
								newImage);
					}
					if (wrapped.getTitleToolTip() != null && wrapped.getTitleToolTip().length() > 0) {
						part.getTransientData()
								.put(IPresentationEngine.OVERRIDE_TITLE_TOOL_TIP_KEY,
								wrapped.getTitleToolTip());
					}
					break;
				case IWorkbenchPartConstants.PROP_DIRTY:
					if (wrapped instanceof ISaveablePart) {
						((MDirtyable) part).setDirty(((ISaveablePart) wrapped).isDirty());
					}
					break;
				case IWorkbenchPartConstants.PROP_INPUT:
					WorkbenchPartReference ref = getReference();
					((WorkbenchPage) ref.getSite().getPage()).firePartInputChanged(ref);
					break;
				}
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

	private void internalDisposeSite(IWorkbenchPartSite site) {
		if (site instanceof PartSite) {
			disposeSite((PartSite) site);
		}
	}

	void disposeSite(PartSite site) {
		site.dispose();
	}

	@Persist
	void doSave() {
		if (wrapped instanceof ISaveablePart) {
			SaveableHelper.savePart((ISaveablePart) wrapped, wrapped, getReference().getSite()
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
		ESelectionService selectionService = (ESelectionService) part.getContext().get(
				ESelectionService.class.getName());
		selectionService.setSelection(e.getSelection());
	}

	protected void clearMenuItems() {
		for (MMenu menu : part.getMenus()) {
			menu.getChildren().clear();
		}
	}
}
