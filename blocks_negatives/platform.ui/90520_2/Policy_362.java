/*******************************************************************************
 * Copyright (c) 2008, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Simon Scholz <simon.scholz@vogella.com> - Bug 462056
 *     Dirk Fauth <dirk.fauth@googlemail.com> - Bug 457939
 *     Alexander Baranov <achilles-86@mail.ru> - Bug 458460
 *     Lars Vogel <Lars.Vogel@vogella.com> - Bug 483842
 *     Patrik Suzzi <psuzzi@gmail.com> - Bug 487621
 *******************************************************************************/
package org.eclipse.e4.ui.internal.workbench.swt;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.InjectionException;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.core.services.statusreporter.StatusReporter;
import org.eclipse.e4.ui.bindings.keys.KeyBindingDispatcher;
import org.eclipse.e4.ui.css.core.util.impl.resources.OSGiResourceLocator;
import org.eclipse.e4.ui.css.swt.dom.WidgetElement;
import org.eclipse.e4.ui.css.swt.engine.CSSSWTEngineImpl;
import org.eclipse.e4.ui.css.swt.helpers.EclipsePreferencesHelper;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;
import org.eclipse.e4.ui.css.swt.theme.IThemeManager;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.PersistState;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.MApplicationElement;
import org.eclipse.e4.ui.model.application.MContribution;
import org.eclipse.e4.ui.model.application.ui.MContext;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MGenericStack;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.services.IStylingEngine;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.IResourceUtilities;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.swt.factories.IRendererFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jface.bindings.keys.SWTKeySupport;
import org.eclipse.jface.bindings.keys.formatting.KeyFormatterFactory;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.testing.TestableObject;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.w3c.dom.Element;
import org.w3c.dom.css.CSSStyleDeclaration;

public class PartRenderingEngine implements IPresentationEngine {
	public static final String EARLY_STARTUP_HOOK = "runEarlyStartup";

			+ "org.eclipse.e4.ui.internal.workbench.swt.PartRenderingEngine";

			+ "org.eclipse.e4.ui.workbench.renderers.swt.WorkbenchRendererFactory";

	public static final String ENABLED_THEME_KEY = "themeEnabled";

	private static boolean enableThemePreference;
	private String factoryUrl;

	IRendererFactory curFactory = null;

	private Map<String, AbstractPartRenderer> customRendererMap = new HashMap<>();

	org.eclipse.swt.widgets.Listener keyListener;

	@Inject
	@Optional
	private void subscribeTopicToBeRendered(@EventTopic(UIEvents.UIElement.TOPIC_TOBERENDERED) Event event) {

		MUIElement changedElement = (MUIElement) event.getProperty(UIEvents.EventTags.ELEMENT);
		MUIElement parent = changedElement.getParent();

		if (parent == null) {
			parent = (MUIElement) ((EObject) changedElement).eContainer();
		}

		if (parent instanceof MMenu) {
			return;
		}

		boolean okToRender = parent instanceof MApplication || parent.getWidget() != null;

		if (changedElement.isToBeRendered() && okToRender) {
			if (Policy.DEBUG_RENDERER) {
				WorkbenchSWTActivator.trace(Policy.DEBUG_RENDERER_FLAG, "visible -> true", null); //$NON-NLS-1$
			}

			Object w = createGui(changedElement);
			if (w instanceof Control && !(w instanceof Shell)) {
				fixZOrder(changedElement);
			}
		} else {
			if (Policy.DEBUG_RENDERER) {
				WorkbenchSWTActivator.trace(Policy.DEBUG_RENDERER_FLAG, "visible -> false", null); //$NON-NLS-1$
			}

			if (parent instanceof MElementContainer<?>) {
				@SuppressWarnings("unchecked")
				MElementContainer<MUIElement> container = (MElementContainer<MUIElement>) parent;
				if (container.getSelectedElement() == changedElement) {
					container.setSelectedElement(null);
				}
			}

			if (okToRender) {
				if (changedElement.getTags().contains(MAXIMIZED)) {
					changedElement.getTags().remove(MAXIMIZED);
				}

				removeGui(changedElement);
			}
		}
	}

	@Inject
	@Optional
	private void subscribeVisibilityHandler(@EventTopic(UIEvents.UIElement.TOPIC_VISIBLE) Event event) {

		MUIElement changedElement = (MUIElement) event.getProperty(UIEvents.EventTags.ELEMENT);
		MUIElement parent = changedElement.getParent();
		if (parent == null) {
			parent = (MUIElement) ((EObject) changedElement).eContainer();
			if (parent == null) {
				return;
			}
		}

		AbstractPartRenderer renderer = (AbstractPartRenderer) parent.getRenderer();
		if (renderer == null || parent instanceof MToolBar) {
			return;
		}

		if (changedElement.isVisible()) {
			if (changedElement.isToBeRendered()) {
				if (changedElement.getWidget() instanceof Control) {
					Composite realComp = (Composite) renderer.getUIContainer(changedElement);
					Control ctrl = (Control) changedElement.getWidget();
					ctrl.setParent(realComp);
					fixZOrder(changedElement);
				}

				if (parent instanceof MElementContainer<?>) {
					@SuppressWarnings("unchecked")
					MElementContainer<MUIElement> container = (MElementContainer<MUIElement>) parent;
					renderer.childRendered(container, changedElement);
				}
			}
		} else {
			if (changedElement.getWidget() instanceof Control) {
				Control ctrl = (Control) changedElement.getWidget();
				ctrl.requestLayout();
				ctrl.setParent(getLimboShell());
			}

			if (parent instanceof MElementContainer<?>) {
				@SuppressWarnings("unchecked")
				MElementContainer<MUIElement> container = (MElementContainer<MUIElement>) parent;
				renderer.hideChild(container, changedElement);
			}
		}
	}

	@Inject
	@Optional
	private void subscribeTrimHandler(@EventTopic(UIEvents.TrimmedWindow.TOPIC_TRIMBARS) Event event) {

		Object changedObj = event.getProperty(UIEvents.EventTags.ELEMENT);
		if (!(changedObj instanceof MTrimmedWindow)) {
			return;
		}

		MTrimmedWindow window = (MTrimmedWindow) changedObj;
		if (window.getWidget() == null) {
			return;
		}

		if (UIEvents.isADD(event)) {
			for (Object o : UIEvents.asIterable(event, UIEvents.EventTags.NEW_VALUE)) {
				MUIElement added = (MUIElement) o;
				if (added.isToBeRendered()) {
					createGui(added, window.getWidget(), window.getContext());
				}
			}
		} else if (UIEvents.isREMOVE(event)) {
			for (Object o : UIEvents.asIterable(event, UIEvents.EventTags.NEW_VALUE)) {
				MUIElement removed = (MUIElement) o;
				if (removed.getRenderer() != null) {
					removeGui(removed);
				}
			}
		}
	}

	@Inject
	@Optional
	private void subscribeChildrenHandler(@EventTopic(UIEvents.ElementContainer.TOPIC_CHILDREN) Event event) {

		Object changedObj = event.getProperty(UIEvents.EventTags.ELEMENT);
		if (!(changedObj instanceof MElementContainer<?>)) {
			return;
		}

		@SuppressWarnings("unchecked")
		MElementContainer<MUIElement> changedElement = (MElementContainer<MUIElement>) changedObj;
		boolean isApplication = changedObj instanceof MApplication;

		boolean menuChild = changedObj instanceof MMenu;
		AbstractPartRenderer renderer = getRendererFor(changedElement);
		if ((!isApplication && renderer == null) || menuChild) {
			return;
		}

		if (UIEvents.isADD(event)) {
			if (Policy.DEBUG_RENDERER) {
				WorkbenchSWTActivator.trace(Policy.DEBUG_RENDERER_FLAG, "Child Added", null); //$NON-NLS-1$
			}
			for (Object o : UIEvents.asIterable(event, UIEvents.EventTags.NEW_VALUE)) {
				MUIElement added = (MUIElement) o;

				boolean isStack = changedObj instanceof MGenericStack<?>;
				boolean hasWidget = added.getWidget() != null;
				boolean isSelected = added == changedElement.getSelectedElement();
				boolean renderIt = !isStack || hasWidget || isSelected;
				if (renderIt) {
					Object w = createGui(added);
					if (w instanceof Control && !(w instanceof Shell)) {
						final Control ctrl = (Control) w;
						fixZOrder(added);
						if (!ctrl.isDisposed()) {
							ctrl.requestLayout();
						}
					}
				} else {
					if (renderer != null && added.isToBeRendered()) {
						renderer.childRendered(changedElement, added);
					}
				}

				int newLocation = modelService.getElementLocation(added);
				if (newLocation == EModelService.IN_SHARED_AREA || newLocation == EModelService.OUTSIDE_PERSPECTIVE) {
					MWindow topWin = modelService.getTopLevelWindowFor(added);
					modelService.hideLocalPlaceholders(topWin, null);
				}
			}
		} else if (UIEvents.isREMOVE(event)) {
			if (Policy.DEBUG_RENDERER) {
				WorkbenchSWTActivator.trace(Policy.DEBUG_RENDERER_FLAG, "Child Removed", null); //$NON-NLS-1$
			}
			for (Object o : UIEvents.asIterable(event, UIEvents.EventTags.OLD_VALUE)) {
				MUIElement removed = (MUIElement) o;
				if (!removed.isToBeRendered()) {
					continue;
				}

				if (removed.getWidget() instanceof Control) {
					Control ctrl = (Control) removed.getWidget();
					ctrl.setLayoutData(null);
					ctrl.getParent().layout(new Control[] { ctrl }, SWT.CHANGED | SWT.DEFER);
				}

				if (changedElement.getSelectedElement() == removed) {
					changedElement.setSelectedElement(null);
				}

				if (renderer != null) {
					renderer.hideChild(changedElement, removed);
				}
			}
		}
	}

	@Inject
	@Optional
	private void subscribeWindowsHandler(@EventTopic(UIEvents.Window.TOPIC_WINDOWS) Event event) {

		subscribeChildrenHandler(event);
	}

	@Inject
	@Optional
	private void subscribePerspectiveWindowsHandler(@EventTopic(UIEvents.Perspective.TOPIC_WINDOWS) Event event) {
		subscribeChildrenHandler(event);
	}

	@Inject
	@Optional
	private void subscribeCssThemeChanged(@EventTopic(IThemeEngine.Events.THEME_CHANGED) Event event) {
		cssThemeChangedHandler.handleEvent(event);
	}

	private IEclipseContext appContext;

	protected Shell testShell;

	protected MApplication theApp;

	@Inject
	EModelService modelService;

	@Inject
	protected Logger logger;

	private Shell limbo;

	private MUIElement removeRoot = null;

	@Inject
	@Optional
	IEventBroker eventBroker;

	private StylingPreferencesHandler cssThemeChangedHandler;

	@Inject
	public PartRenderingEngine(
			@Named(E4Workbench.RENDERER_FACTORY_URI) @Optional String factoryUrl) {
		if (factoryUrl == null) {
			factoryUrl = defaultFactoryUrl;
		}
		this.factoryUrl = factoryUrl;
	}

	protected void fixZOrder(MUIElement element) {
		MElementContainer<MUIElement> parent = element.getParent();
		if (parent == null) {
			Object econtainer = ((EObject) element).eContainer();
			if (econtainer instanceof MElementContainer<?>) {
				@SuppressWarnings("unchecked")
				MElementContainer<MUIElement> container = (MElementContainer<MUIElement>) econtainer;
				parent = container;
			}
		}
		if (parent == null || !(element.getWidget() instanceof Control)) {
			return;
		}

		Control elementCtrl = (Control) element.getWidget();
		Control prevCtrl = null;
		for (MUIElement kid : parent.getChildren()) {
			if (kid == element) {
				if (prevCtrl != null) {
					elementCtrl.moveBelow(prevCtrl);
				} else {
					elementCtrl.moveAbove(null);
				}
				break;
			} else if (kid.getWidget() instanceof Control && kid.isVisible()) {
				prevCtrl = (Control) kid.getWidget();
			}
		}

		Object widget = parent.getWidget();
		if (widget instanceof Composite) {
			Composite composite = (Composite) widget;
			if (composite.getShell() == elementCtrl.getShell()) {
				Composite temp = elementCtrl.getParent();
				while (temp != composite) {
					if (temp == null) {
						return;
					}
					temp = temp.getParent();
				}
				composite.layout(true, true);
			}
		}
	}

	/**
	 * Initialize a part renderer from the extension point.
	 *
	 * @param context
	 *            the context for the part factories
	 */
	@PostConstruct
	void initialize(IEclipseContext context) {
		this.appContext = context;

		KeyFormatterFactory.setDefault(SWTKeySupport.getKeyFormatterForPlatform());

		context.set(IPresentationEngine.class, this);

		IRendererFactory factory = null;
		IContributionFactory contribFactory = context.get(IContributionFactory.class);
		try {
			factory = (IRendererFactory) contribFactory.create(factoryUrl, context);
		} catch (Exception e) {
			logger.warn(e, "Could not create rendering factory");
		}

		if (factory == null) {
			try {
				factory = (IRendererFactory) contribFactory.create(defaultFactoryUrl, context);
			} catch (Exception e) {
				logger.error(e, "Could not create default rendering factory");
			}
		}

		if (factory == null) {
			throw new IllegalStateException("Could not create any rendering factory. Aborting ...");
		}

		curFactory = factory;
		context.set(IRendererFactory.class, curFactory);

		IScopeContext[] contexts = new IScopeContext[] { DefaultScope.INSTANCE, InstanceScope.INSTANCE};
		enableThemePreference = Platform.getPreferencesService().getBoolean("org.eclipse.e4.ui.workbench.renderers.swt",
				ENABLED_THEME_KEY, true, contexts);

		cssThemeChangedHandler = new StylingPreferencesHandler(context.get(Display.class));
	}

	private static void populateModelInterfaces(MContext contextModel,
			IEclipseContext context, Class<?>[] interfaces) {
		for (Class<?> intf : interfaces) {
			if (Policy.DEBUG_CONTEXTS) {
				WorkbenchSWTActivator.trace(Policy.DEBUG_CONTEXTS_FLAG,
								+ contextModel.getClass().getName(), null);
			}
			context.set(intf.getName(), contextModel);

			populateModelInterfaces(contextModel, context, intf.getInterfaces());
		}
	}

	private String getContextName(MUIElement element) {
		StringBuilder builder = new StringBuilder(element.getClass()
				.getSimpleName());
		String elementId = element.getElementId();
		if (elementId != null && elementId.length() != 0) {
			builder.append(" (").append(elementId).append(") ");
		}
		builder.append("Context");
		return builder.toString();
	}

	@Override
	public Object createGui(final MUIElement element,
			final Object parentWidget, final IEclipseContext parentContext) {
		final Object[] gui = { null };
		SafeRunner.run(new ISafeRunnable() {
			@Override
			public void handleException(Throwable e) {
				if (e instanceof Error) {
					throw (Error) e;
				}
				if (logger != null) {
					logger.error(e, NLS.bind(message, element));
				}
			}

			@Override
			public void run() throws Exception {
				gui[0] = safeCreateGui(element, parentWidget, parentContext);
			}
		});
		return gui[0];
	}

	public Object safeCreateGui(MUIElement element, Object parentWidget,
			IEclipseContext parentContext) {
		if (!element.isToBeRendered())
			return null;

		if (removeRoot != null) {
			return null;
		}

		Object currentWidget = element.getWidget();
		if (currentWidget != null) {
			if (currentWidget instanceof Control) {
				Control control = (Control) currentWidget;
				MUIElement elementParent = element.getParent();
				if (!(element instanceof MPlaceholder)
						|| !(elementParent instanceof MPartStack))
					control.setVisible(true);

				if (parentWidget instanceof Composite) {
					Composite currentParent = control.getParent();
					if (currentParent != parentWidget) {
						if (currentParent instanceof CTabFolder) {
							CTabFolder folder = (CTabFolder) currentParent;
							if (folder.getTopRight() == control) {
								folder.setTopRight(null);
							}
						}

						control.setParent((Composite) parentWidget);
					}
				}
			}

			if (element instanceof MContext) {
				IEclipseContext ctxt = ((MContext) element).getContext();
				if (ctxt != null)
					ctxt.setParent(parentContext);
			} else {
				List<MContext> childContexts = modelService.findElements(
						element, null, MContext.class, null);
				for (MContext c : childContexts) {
					MUIElement kid = (MUIElement) c;
					MUIElement parent = kid.getParent();
					if (parent == null && kid.getCurSharedRef() != null)
						parent = kid.getCurSharedRef().getParent();
					if (!(element instanceof MPlaceholder) && parent != element)
						continue;

					if (c.getContext() != null
							&& c.getContext().getParent() != parentContext) {
						c.getContext().setParent(parentContext);
					}
				}
			}

			MElementContainer<MUIElement> parentElement = element.getParent();
			if (parentElement != null) {
				AbstractPartRenderer parentRenderer = getRendererFor(parentElement);
				if (parentRenderer != null) {
					parentRenderer.childRendered(parentElement, element);
				}
			}
			return element.getWidget();
		}

		if (element instanceof MContext) {
			MContext ctxt = (MContext) element;
			if (ctxt.getContext() == null) {
				IEclipseContext lclContext = parentContext
						.createChild(getContextName(element));
				populateModelInterfaces(ctxt, lclContext, element.getClass()
						.getInterfaces());
				ctxt.setContext(lclContext);


				for (String variable : ctxt.getVariables()) {
					lclContext.declareModifiable(variable);
				}

				Map<String, String> props = ctxt.getProperties();
				for (Entry<String, String> entry : props.entrySet()) {
					lclContext.set(entry.getKey(), entry.getValue());
				}
			}
		}

		if (element.getWidget() != null) {
			return safeCreateGui(element, parentWidget, parentContext);
		}

		Object newWidget = createWidget(element, parentWidget);

		if (newWidget != null) {
			AbstractPartRenderer renderer = getRendererFor(element);

			renderer.hookControllerLogic(element);

			if (element instanceof MElementContainer) {
				@SuppressWarnings("unchecked")
				MElementContainer<MUIElement> container = (MElementContainer<MUIElement>) element;
				renderer.processContents(container);
			}

			renderer.postProcess(element);

			MElementContainer<MUIElement> parentElement = element.getParent();
			if (parentElement != null) {
				AbstractPartRenderer parentRenderer = getRendererFor(parentElement);
				if (parentRenderer != null) {
					parentRenderer.childRendered(parentElement, element);
				}
			}
		} else {
			if (element instanceof MContext) {
				MContext ctxt = (MContext) element;
				IEclipseContext lclContext = ctxt.getContext();
				if (lclContext != null) {
					lclContext.dispose();
					ctxt.setContext(null);
				}
			}
		}

		return newWidget;
	}

	private IEclipseContext getContext(MUIElement parent) {
		if (parent instanceof MContext) {
			return ((MContext) parent).getContext();
		}
		return modelService.getContainingContext(parent);
	}

	@Override
	public Object createGui(final MUIElement element) {
		final Object[] gui = { null };
		SafeRunner.run(new ISafeRunnable() {
			@Override
			public void handleException(Throwable e) {
				if (e instanceof Error) {
					throw (Error) e;
				}
				if (logger != null) {
					logger.error(e, NLS.bind(message, element));
				}
			}

			@Override
			public void run() throws Exception {
				gui[0] = safeCreateGui(element);
			}
		});
		return gui[0];
	}

	private Object safeCreateGui(MUIElement element) {
		Object parent = null;
		MUIElement parentME = element.getParent();
		if (parentME == null)
			parentME = (MUIElement) ((EObject) element).eContainer();
		if (parentME != null) {
			AbstractPartRenderer renderer = getRendererFor(parentME);
			if (renderer != null) {
				if (!element.isVisible()) {
					parent = getLimboShell();
				} else {
					parent = renderer.getUIContainer(element);
				}
			}
		}

		IEclipseContext parentContext = null;
		if (element.getCurSharedRef() != null) {
			MPlaceholder ph = element.getCurSharedRef();
			parentContext = getContext(ph.getParent());
		} else if (parentContext == null && element.getParent() != null) {
			parentContext = getContext(element.getParent());
		} else if (parentContext == null && element.getParent() == null) {
			parentContext = getContext((MUIElement) ((EObject) element)
					.eContainer());
		}

		return safeCreateGui(element, parent, parentContext);
	}

	@Override
	public void focusGui(MUIElement element) {
		AbstractPartRenderer renderer = (AbstractPartRenderer) element
				.getRenderer();
		if (renderer == null || element.getWidget() == null)
			return;

		Object implementation = element instanceof MContribution ? ((MContribution) element)
				.getObject() : null;

		if (implementation == null) {
			renderer.forceFocus(element);
			return;
		}

		try {
			IEclipseContext context = getContext(element);
			Object defaultValue = new Object();
			Object returnValue = ContextInjectionFactory.invoke(implementation,
					Focus.class, context, defaultValue);
			if (returnValue == defaultValue) {
				renderer.forceFocus(element);
			}
		} catch (InjectionException e) {
			log("Failed to grant focus to element", "Failed to grant focus to element ({0})", //$NON-NLS-1$ //$NON-NLS-2$
					element.getElementId(), e);
		} catch (RuntimeException e) {
			log("Failed to grant focus to element via DI", //$NON-NLS-1$
					"Failed to grant focus via DI to element ({0})", element.getElementId(), e); //$NON-NLS-1$
		}
	}

	private void log(String unidentifiedMessage, String identifiedMessage,
			String id, Exception e) {
		if (id == null || id.length() == 0) {
			logger.error(e, unidentifiedMessage);
		} else {
			logger.error(e, NLS.bind(identifiedMessage, id));
		}
	}

	private Shell getLimboShell() {
		if (limbo == null) {
			limbo = new Shell(Display.getCurrent(), SWT.NONE);

			limbo.setLocation(0, 10000);

			limbo.setBackgroundMode(SWT.INHERIT_DEFAULT);
			limbo.setData(ShellActivationListener.DIALOG_IGNORE_KEY,
					Boolean.TRUE);
		}
		return limbo;
	}

	/**
	 * @param element
	 */
	@Override
	public void removeGui(final MUIElement element) {
		SafeRunner.run(new ISafeRunnable() {
			@Override
			public void handleException(Throwable e) {
				if (e instanceof Error) {
					throw (Error) e;
				}
				if (logger != null) {
					logger.error(e, NLS.bind(message, element));
				}
			}

			@Override
			public void run() throws Exception {
				safeRemoveGui(element);
			}
		});
	}

	private void safeRemoveGui(MUIElement element) {
		if (removeRoot == null)
			removeRoot = element;

		MUIElement parent = element.getParent();
		AbstractPartRenderer parentRenderer = parent != null ? getRendererFor(parent)
				: null;
		if (parentRenderer != null) {
			parentRenderer.hideChild(element.getParent(), element);
		}

		AbstractPartRenderer renderer = getRendererFor(element);

		if (renderer != null) {

			if (element instanceof MElementContainer<?>) {
				@SuppressWarnings("unchecked")
				MElementContainer<MUIElement> container = (MElementContainer<MUIElement>) element;
				MUIElement selectedElement = container.getSelectedElement();
				List<MUIElement> children = container.getChildren();
				for (MUIElement child : new ArrayList<>(children)) {
					if (child != selectedElement) {
						removeGui(child);
					}
				}

				if (selectedElement != null
						&& children.contains(selectedElement)) {
					removeGui(selectedElement);
				}
			}

			if (element instanceof MPerspective) {
				MPerspective perspective = (MPerspective) element;
				for (MWindow subWindow : perspective.getWindows()) {
					removeGui(subWindow);
				}
			} else if (element instanceof MWindow) {
				MWindow window = (MWindow) element;
				for (MWindow subWindow : window.getWindows()) {
					removeGui(subWindow);
				}

				if (window instanceof MTrimmedWindow) {
					MTrimmedWindow trimmedWindow = (MTrimmedWindow) window;
					for (MUIElement trimBar : trimmedWindow.getTrimBars()) {
						removeGui(trimBar);
					}
				}
			}

			if (element instanceof MContribution) {
				MContribution contribution = (MContribution) element;
				Object client = contribution.getObject();
				IEclipseContext parentContext = renderer.getContext(element);
				if (parentContext != null && client != null) {
					try {
						ContextInjectionFactory.invoke(client, PersistState.class, parentContext, null);
					} catch (Exception e) {
						if (logger != null) {
							logger.error(e);
						}
					}
				}
			}

			renderer.disposeWidget(element);

			if (element instanceof MContribution) {
				MContribution contribution = (MContribution) element;
				Object client = contribution.getObject();
				IEclipseContext parentContext = renderer.getContext(element);
				if (parentContext != null && client != null) {
					try {
						ContextInjectionFactory.uninject(client, parentContext);
					} catch (Exception e) {
						if (logger != null) {
							logger.error(e);
						}
					}
				}
				contribution.setObject(null);
			}

			if (element instanceof MContext) {
				clearContext((MContext) element);
			}
		}

		if (element instanceof MPlaceholder) {
			MPlaceholder ph = (MPlaceholder) element;
			if (ph.getRef() != null && ph.getRef().getCurSharedRef() == ph) {
				ph.getRef().setCurSharedRef(null);
			}
		}

		if (removeRoot == element)
			removeRoot = null;
	}

	private void clearContext(MContext contextME) {
		MContext ctxt = contextME;
		IEclipseContext lclContext = ctxt.getContext();
		if (lclContext != null) {
			IEclipseContext parentContext = lclContext.getParent();
			IEclipseContext child = parentContext != null ? parentContext
					.getActiveChild() : null;
			if (child == lclContext) {
				child.deactivate();
			}

			ctxt.setContext(null);
			lclContext.dispose();
		}
	}

	protected Object createWidget(MUIElement element, Object parent) {
		AbstractPartRenderer renderer = getRenderer(element, parent);
		if (renderer != null) {
			element.setRenderer(renderer);
			Object newWidget = renderer.createWidget(element, parent);
			if (newWidget != null) {
				renderer.bindWidget(element, newWidget);
				return newWidget;
			}
		}

		return null;
	}

	private AbstractPartRenderer getRenderer(MUIElement uiElement, Object parent) {
		String customURI = uiElement.getPersistedState().get(IPresentationEngine.CUSTOM_RENDERER_KEY);
		if (customURI != null) {
			AbstractPartRenderer abstractPartRenderer = customRendererMap.get(customURI);
			if (abstractPartRenderer != null) {
				return abstractPartRenderer;
			}

			IEclipseContext owningContext = modelService.getContainingContext(uiElement);
			IContributionFactory contributionFactory = owningContext.get(IContributionFactory.class);
			Object customRenderer = contributionFactory.create(customURI, owningContext);
			if (customRenderer instanceof AbstractPartRenderer) {
				customRendererMap.put(customURI, (AbstractPartRenderer) customRenderer);
				return (AbstractPartRenderer) customRenderer;
			}
		}

		return curFactory.getRenderer(uiElement, parent);
	}

	protected AbstractPartRenderer getRendererFor(MUIElement element) {
		return (AbstractPartRenderer) element.getRenderer();
	}

	@Override
	@Inject
	@Optional
	public Object run(final MApplicationElement uiRoot, final IEclipseContext runContext) {
		final Display display;
		if (runContext.get(Display.class) != null) {
			display = runContext.get(Display.class);
		} else {
			display = Display.getDefault();
			runContext.set(Display.class, display);
		}
		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {

			@Override
			public void run() {
				initializeStyling(display, runContext);

				runContext.set(IResourceUtilities.class, new ResourceUtility());

				KeyBindingDispatcher dispatcher = ContextInjectionFactory.make(KeyBindingDispatcher.class, runContext);
				runContext.set(KeyBindingDispatcher.class, dispatcher);
				keyListener = dispatcher.getKeyDownFilter();
				display.addFilter(SWT.KeyDown, keyListener);
				display.addFilter(SWT.Traverse, keyListener);


				Shell limbo = getLimboShell();
				runContext.set("limbo", limbo);

				testShell = null;
				theApp = null;
				boolean spinOnce = true;
				if (uiRoot instanceof MApplication) {
					ShellActivationListener shellDialogListener = new ShellActivationListener((MApplication) uiRoot);
					display.addFilter(SWT.Activate, shellDialogListener);
					display.addFilter(SWT.Deactivate, shellDialogListener);
					theApp = (MApplication) uiRoot;
					for (MWindow window : theApp.getChildren()) {
						createGui(window);
					}

					IApplicationContext ac = appContext.get(IApplicationContext.class);
					if (ac != null) {
						ac.applicationRunning();
						if (eventBroker != null) {
							eventBroker.post(
									UIEvents.UILifeCycle.APP_STARTUP_COMPLETE,
									theApp);
						}
					}
				} else if (uiRoot instanceof MUIElement) {
					if (uiRoot instanceof MWindow) {
						testShell = (Shell) createGui((MUIElement) uiRoot);
					} else {
						testShell = new Shell(display, SWT.SHELL_TRIM);
						createGui((MUIElement) uiRoot, testShell, null);
					}
				}

				Runnable earlyStartup = (Runnable) runContext.get(EARLY_STARTUP_HOOK);
				if (earlyStartup != null) {
					earlyStartup.run();
				}

				TestableObject testableObject = runContext.get(TestableObject.class);
				if (testableObject instanceof E4Testable) {
					((E4Testable) testableObject).init(display, runContext.get(IWorkbench.class));
				}

				IEventLoopAdvisor advisor = runContext.getActiveLeaf().get(IEventLoopAdvisor.class);
				if (advisor == null) {
					advisor = new IEventLoopAdvisor() {
						@Override
						public void eventLoopIdle(Display display) {
							display.sleep();
						}

						@Override
						public void eventLoopException(Throwable exception) {
							StatusReporter statusReporter = appContext.get(StatusReporter.class);
							if (statusReporter != null) {
								statusReporter.show(StatusReporter.ERROR, "Internal Error", exception);
							} else {
								if (logger != null) {
									logger.error(exception);
								}
							}
						}
					};
				}
				final IEventLoopAdvisor finalAdvisor = advisor;
				display.setErrorHandler(e -> {
					if (e instanceof LinkageError || e instanceof AssertionError) {
						handle(e, finalAdvisor);
					} else {
						throw e;
					}
				});
				display.setRuntimeExceptionHandler(e -> handle(e, finalAdvisor));
				while (((testShell != null && !testShell.isDisposed()) || (theApp != null && someAreVisible(theApp
						.getChildren()))) && !display.isDisposed()) {
					try {
						if (!display.readAndDispatch()) {
							runContext.processWaiting();
							if (spinOnce) {
								return;
							}
							advisor.eventLoopIdle(display);
						}
					} catch (ThreadDeath th) {
						throw th;
					} catch (Exception ex) {
						handle(ex, advisor);
					} catch (Error err) {
						handle(err, advisor);
					}
				}
				if (!spinOnce) {
					cleanUp();
				}
			}

			private void handle(Throwable ex, IEventLoopAdvisor advisor) {
				try {
					advisor.eventLoopException(ex);
				} catch (Throwable t) {
					if (t instanceof ThreadDeath) {
						throw (ThreadDeath) t;
					}

					t.printStackTrace();
				}
			}
		});

		return IApplication.EXIT_OK;
	}

	protected boolean someAreVisible(List<MWindow> windows) {

		final int limit = windows.size();
		for (int i = 0; i < limit; i++) {
			final MWindow win = windows.get(i);
			if (win.isToBeRendered() && win.getWidget() != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void stop() {
		cleanUp();
		if (theApp != null) {
			for (MWindow window : theApp.getChildren()) {
				if (window.getWidget() != null) {
					removeGui(window);
				}
			}
		} else if (testShell != null && !testShell.isDisposed()) {
			Object model = testShell.getData(AbstractPartRenderer.OWNING_ME);
			if (model instanceof MUIElement) {
				removeGui((MUIElement) model);
			} else {
				testShell.close();
			}
		}
	}

	/*
	 * There are situations where this is called more than once until we know
	 * why this is needed we should make this safe for multiple calls
	 */
	private void cleanUp() {
		if (keyListener != null) {
			Display display = Display.getDefault();
			if (!display.isDisposed()) {
				display.removeFilter(SWT.KeyDown, keyListener);
				display.removeFilter(SWT.Traverse, keyListener);
				keyListener = null;
			}
		}
	}

	public static void initializeStyling(Display display,
			IEclipseContext appContext) {
		String cssTheme = (String) appContext.get(E4Application.THEME_ID);
		String cssURI = (String) appContext.get(IWorkbench.CSS_URI_ARG);
		if ("none".equals(cssTheme) || (!enableThemePreference)) {
			appContext.set(IStylingEngine.SERVICE_NAME, new IStylingEngine() {
				@Override
				public void setClassname(Object widget, String classname) {
					WidgetElement.setCSSClass((Widget) widget, classname);
				}

				@Override
				public void setId(Object widget, String id) {
					WidgetElement.setID((Widget) widget, id);
				}

				@Override
				public void style(Object widget) {
				}

				@Override
				public CSSStyleDeclaration getStyle(Object widget) {
					return null;
				}

				@Override
				public void setClassnameAndId(Object widget, String classname,
						String id) {
					WidgetElement.setCSSClass((Widget) widget, classname);
					WidgetElement.setID((Widget) widget, id);
				}
			});
		} else if (cssTheme != null) {
			final IThemeEngine themeEngine = createThemeEngine(display, appContext);
			String cssResourcesURI = (String) appContext.get(IWorkbench.CSS_RESOURCE_URI_ARG);

			if (cssResourcesURI != null) {
				themeEngine.registerResourceLocator(new OSGiResourceLocator(cssResourcesURI));
			}

			appContext.set(IStylingEngine.SERVICE_NAME, new IStylingEngine() {
				@Override
				public void setClassname(Object widget, String classname) {
					WidgetElement.setCSSClass((Widget) widget, classname);
					themeEngine.applyStyles(widget, true);
				}

				@Override
				public void setId(Object widget, String id) {
					WidgetElement.setID((Widget) widget, id);
					themeEngine.applyStyles(widget, true);
				}

				@Override
				public void style(Object widget) {
					themeEngine.applyStyles(widget, true);
				}

				@Override
				public CSSStyleDeclaration getStyle(Object widget) {
					return themeEngine.getStyle(widget);
				}

				@Override
				public void setClassnameAndId(Object widget, String classname, String id) {
					WidgetElement.setCSSClass((Widget) widget, classname);
					WidgetElement.setID((Widget) widget, id);
					themeEngine.applyStyles(widget, true);
				}
			});

			setCSSTheme(display, themeEngine, cssTheme);

		} else if (cssURI != null) {
			String cssResourcesURI = (String) appContext.get(IWorkbench.CSS_RESOURCE_URI_ARG);
			final CSSSWTEngineImpl cssEngine = new CSSSWTEngineImpl(display, true);
			WidgetElement.setEngine(display, cssEngine);
			if (cssResourcesURI != null) {
				cssEngine.getResourcesLocatorManager().registerResourceLocator(
						new OSGiResourceLocator(cssResourcesURI.toString()));
			}
			display.setData("org.eclipse.e4.ui.css.context", appContext); //$NON-NLS-1$
			appContext.set(IStylingEngine.SERVICE_NAME, new IStylingEngine() {
				@Override
				public void setClassname(Object widget, String classname) {
					WidgetElement.setCSSClass((Widget) widget, classname);
					cssEngine.applyStyles(widget, true);
				}

				@Override
				public void setId(Object widget, String id) {
					WidgetElement.setID((Widget) widget, id);
					cssEngine.applyStyles(widget, true);
				}

				@Override
				public void style(Object widget) {
					cssEngine.applyStyles(widget, true);
				}

				@Override
				public CSSStyleDeclaration getStyle(Object widget) {
					Element e = cssEngine.getCSSElementContext(widget).getElement();
					if (e == null) {
						return null;
					}
					return cssEngine.getViewCSS().getComputedStyle(e, null);
				}

				@Override
				public void setClassnameAndId(Object widget, String classname, String id) {
					WidgetElement.setCSSClass((Widget) widget, classname);
					WidgetElement.setID((Widget) widget, id);
					cssEngine.applyStyles(widget, true);
				}
			});

			URL url;
			InputStream stream = null;
			try {
				url = FileLocator.resolve(new URL(cssURI));
				stream = url.openStream();
				cssEngine.parseStyleSheet(stream);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (stream != null) {
					try {
						stream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			Shell[] shells = display.getShells();
			for (Shell s : shells) {
				try {
					s.setRedraw(false);
					s.reskin(SWT.ALL);
					cssEngine.applyStyles(s, true);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					s.setRedraw(true);
				}
			}
		}

		CSSRenderingUtils cssUtils = ContextInjectionFactory.make(CSSRenderingUtils.class, appContext);
		appContext.set(CSSRenderingUtils.class, cssUtils);
	}

	private static IThemeEngine createThemeEngine(Display display, IEclipseContext appContext) {
		IContributionFactory contribution = appContext.get(IContributionFactory.class);
		IEclipseContext cssContext = EclipseContextFactory.create();
		cssContext.set(IContributionFactory.class, contribution);
		display.setData("org.eclipse.e4.ui.css.context", cssContext); //$NON-NLS-1$

		IThemeManager mgr = appContext.get(IThemeManager.class);
		IThemeEngine themeEngine = mgr.getEngineForDisplay(display);

		appContext.set(IThemeEngine.class, themeEngine);
		return themeEngine;
	}

	private static void setCSSTheme(Display display, IThemeEngine themeEngine, String cssTheme) {
		if (display.getHighContrast()) {
			themeEngine.setTheme(cssTheme, false);
		} else {
			themeEngine.restore(cssTheme);
		}
	}

	public static class StylingPreferencesHandler implements EventHandler {
		private HashSet<IEclipsePreferences> prefs = null;

		public StylingPreferencesHandler(Display display) {
			if (display != null) {
				display.addListener(SWT.Dispose, createOnDisplayDisposedListener());
			}
		}

		protected Listener createOnDisplayDisposedListener() {
			return event -> resetOverriddenPreferences();
		}

		@Override
		public void handleEvent(Event event) {
			resetOverriddenPreferences();
			overridePreferences(getThemeEngine(event));
		}

		protected void resetOverriddenPreferences() {
			for (IEclipsePreferences preferences : getPreferences()) {
				resetOverriddenPreferences(preferences);
			}
		}

		protected void resetOverriddenPreferences(IEclipsePreferences preferences) {
			for (String name : getOverriddenPropertyNames(preferences)) {
				preferences.remove(name);
			}
			removeOverriddenPropertyNames(preferences);
		}

		protected void removeOverriddenPropertyNames(IEclipsePreferences preferences) {
			EclipsePreferencesHelper.removeOverriddenPropertyNames(preferences);
		}

		protected List<String> getOverriddenPropertyNames(IEclipsePreferences preferences) {
			return EclipsePreferencesHelper.getOverriddenPropertyNames(preferences);
		}

		protected Set<IEclipsePreferences> getPreferences() {
			if (prefs == null) {
				prefs = new HashSet<>();
				BundleContext context = WorkbenchSWTActivator.getDefault().getContext();
				for (Bundle bundle : context.getBundles()) {
					if (bundle.getSymbolicName() != null) {
						prefs.add(InstanceScope.INSTANCE.getNode(bundle.getSymbolicName()));
					}
				}
			}
			return prefs;
		}

		private void overridePreferences(IThemeEngine themeEngine) {
			if (themeEngine != null) {
				for (IEclipsePreferences preferences : getPreferences()) {
					themeEngine.applyStyles(preferences, false);
				}
			}
		}

		private IThemeEngine getThemeEngine(Event event) {
			return (IThemeEngine) event.getProperty(IThemeEngine.Events.THEME_ENGINE);
		}
	}
}
