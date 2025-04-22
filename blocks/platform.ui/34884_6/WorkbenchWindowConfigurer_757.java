
package org.eclipse.ui.internal;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionInfo;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;
import org.eclipse.e4.core.commands.internal.HandlerServiceImpl;
import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.contexts.RunAndTrack;
import org.eclipse.e4.core.di.InjectionException;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.internal.workbench.OpaqueElementUtil;
import org.eclipse.e4.ui.internal.workbench.PartServiceSaveHandler;
import org.eclipse.e4.ui.internal.workbench.URIHelper;
import org.eclipse.e4.ui.internal.workbench.renderers.swt.IUpdateService;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.SideValue;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimElement;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuSeparator;
import org.eclipse.e4.ui.model.application.ui.menu.MToolControl;
import org.eclipse.e4.ui.model.internal.Position;
import org.eclipse.e4.ui.model.internal.PositionInfo;
import org.eclipse.e4.ui.services.EContextService;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ISaveHandler;
import org.eclipse.e4.ui.workbench.modeling.IWindowCloseHandler;
import org.eclipse.e4.ui.workbench.renderers.swt.MenuManagerRenderer;
import org.eclipse.e4.ui.workbench.renderers.swt.MenuManagerRendererFilter;
import org.eclipse.e4.ui.workbench.renderers.swt.ToolBarManagerRenderer;
import org.eclipse.e4.ui.workbench.renderers.swt.TrimBarLayout;
import org.eclipse.e4.ui.workbench.renderers.swt.TrimmedPartLayout;
import org.eclipse.e4.ui.workbench.swt.factories.IRendererFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.action.AbstractGroupMarker;
import org.eclipse.jface.action.CoolBarManager;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IContributionManagerOverrides;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.SubContributionItem;
import org.eclipse.jface.commands.ActionHandler;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.internal.provisional.action.CoolBarManager2;
import org.eclipse.jface.internal.provisional.action.ICoolBarManager2;
import org.eclipse.jface.internal.provisional.action.IToolBarManager2;
import org.eclipse.jface.internal.provisional.action.ToolBarManager2;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.operation.ModalContext;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.osgi.util.NLS;
import org.eclipse.osgi.util.TextProcessor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ActiveShellExpression;
import org.eclipse.ui.IPageListener;
import org.eclipse.ui.IPageService;
import org.eclipse.ui.IPartService;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.ISaveablesLifecycleListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.StartupThreading.StartupRunnable;
import org.eclipse.ui.internal.actions.CommandAction;
import org.eclipse.ui.internal.commands.SlaveCommandService;
import org.eclipse.ui.internal.contexts.ContextService;
import org.eclipse.ui.internal.dialogs.CustomizePerspectiveDialog;
import org.eclipse.ui.internal.e4.compatibility.CompatibilityPart;
import org.eclipse.ui.internal.e4.compatibility.ModeledPageLayout;
import org.eclipse.ui.internal.e4.compatibility.SelectionService;
import org.eclipse.ui.internal.handlers.ActionCommandMappingService;
import org.eclipse.ui.internal.handlers.IActionCommandMappingService;
import org.eclipse.ui.internal.handlers.LegacyHandlerService;
import org.eclipse.ui.internal.layout.ITrimManager;
import org.eclipse.ui.internal.layout.IWindowTrim;
import org.eclipse.ui.internal.menus.IActionSetsListener;
import org.eclipse.ui.internal.menus.LegacyActionPersistence;
import org.eclipse.ui.internal.menus.MenuHelper;
import org.eclipse.ui.internal.menus.SlaveMenuService;
import org.eclipse.ui.internal.menus.WorkbenchMenuService;
import org.eclipse.ui.internal.misc.UIListenerLogging;
import org.eclipse.ui.internal.progress.ProgressRegion;
import org.eclipse.ui.internal.provisional.application.IActionBarConfigurer2;
import org.eclipse.ui.internal.registry.IActionSetDescriptor;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.registry.UIExtensionTracker;
import org.eclipse.ui.internal.services.EvaluationReference;
import org.eclipse.ui.internal.services.IServiceLocatorCreator;
import org.eclipse.ui.internal.services.IWorkbenchLocationService;
import org.eclipse.ui.internal.services.ServiceLocator;
import org.eclipse.ui.internal.services.WorkbenchLocationService;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.IMenuService;
import org.eclipse.ui.services.IDisposable;
import org.eclipse.ui.services.IEvaluationService;
import org.eclipse.ui.services.IServiceScopes;
import org.eclipse.ui.views.IViewDescriptor;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
public class WorkbenchWindow implements IWorkbenchWindow {

	public static final String PERSPECTIVE_SPACER_ID = "PerspectiveSpacer"; //$NON-NLS-1$

	private static final String MAIN_TOOLBAR_ID = "org.eclipse.ui.main.toolbar"; //$NON-NLS-1$
	private static final String COMMAND_ID_TOGGLE_COOLBAR = "org.eclipse.ui.ToggleCoolbarAction"; //$NON-NLS-1$

	public static final String ACTION_SET_CMD_PREFIX = "AS::"; //$NON-NLS-1$

	@Inject
	private IWorkbench workbench;
	@Inject
	private MTrimmedWindow model;
	@Inject
	private IPresentationEngine engine;

	@Inject
	private IRendererFactory rendererFactory;

	@Inject
	private MApplication application;

	@Inject
	EModelService modelService;

	@Inject
	private IEventBroker eventBroker;

	@Inject
	@Optional
	private Logger logger;

	@Inject
	private IExtensionRegistry extensionRegistry;

	private WorkbenchPage page;

	private WorkbenchWindowAdvisor windowAdvisor;

	private ActionBarAdvisor actionBarAdvisor;

	private PageListenerList pageListeners = new PageListenerList();

	private PerspectiveListenerList perspectiveListeners = new PerspectiveListenerList();

	private PartService partService = new PartService();

	private WWinActionBars actionBars;

	private boolean updateDisabled = false;

	private boolean closing = false;

	private boolean shellActivated = false;

	ProgressRegion progressRegion = null;

	private List<MTrimElement> workbenchTrimElements = new ArrayList<MTrimElement>();

	private Map<MToolControl, IConfigurationElement> iceMap = new HashMap<MToolControl, IConfigurationElement>();

	public IConfigurationElement getICEFor(MToolControl mtc) {
		return iceMap.get(mtc);
	}

	private ServiceLocator serviceLocator;

	private int submenus = 0x00;

	private WorkbenchWindowConfigurer windowConfigurer = null;

	private ListenerList genericPropertyListeners = new ListenerList();

	private IAdaptable input;

	private IPerspectiveDescriptor perspective;

	private EventHandler windowWidgetHandler = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			if (event.getProperty(UIEvents.EventTags.ELEMENT) == model
					&& event.getProperty(UIEvents.EventTags.NEW_VALUE) == null) {
				manageChanges = false;
				canUpdateMenus = false;
				menuUpdater = null;

				MMenu menu = model.getMainMenu();
				if (menu != null) {
					engine.removeGui(menu);
					model.setMainMenu(null);
				}

				eventBroker.unsubscribe(windowWidgetHandler);
			}
		}
	};

	static final String TEXT_DELIMITERS = TextProcessor.getDefaultDelimiters() + "-"; //$NON-NLS-1$

	static final String GRP_PAGES = "pages"; //$NON-NLS-1$

	static final String GRP_PERSPECTIVES = "perspectives"; //$NON-NLS-1$

	static final String GRP_FAST_VIEWS = "fastViews"; //$NON-NLS-1$

	static final int VGAP = 0;

	static final int CLIENT_INSET = 3;

	static final int BAR_SIZE = 23;

	static final String MOVE_TAG = "move_"; //$NON-NLS-1$

	private static final List<String> QUICK_ACCESS_ELEMENT_IDS = Collections
			.unmodifiableList(Arrays.asList("Spacer Glue", "SearchField", "Search-PS Glue")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	public static final String PROP_COOLBAR_VISIBLE = "coolbarVisible"; //$NON-NLS-1$

	public static final String PROP_PERSPECTIVEBAR_VISIBLE = "perspectiveBarVisible"; //$NON-NLS-1$

	public static final String PROP_STATUS_LINE_VISIBLE = "statusLineVisible"; //$NON-NLS-1$

	public static final int SHOW_VIEW_SUBMENU = 0x01;

	public static final int OPEN_PERSPECTIVE_SUBMENU = 0x02;

	public static final int NEW_WIZARD_SUBMENU = 0x04;

	public void addSubmenu(int type) {
		submenus |= type;
	}

	public boolean containsSubmenu(int type) {
		return ((submenus & type) != 0);
	}

	private static final int FILL_ALL_ACTION_BARS = ActionBarAdvisor.FILL_MENU_BAR
			| ActionBarAdvisor.FILL_COOL_BAR | ActionBarAdvisor.FILL_STATUS_LINE;

	public WorkbenchWindow(IAdaptable input, IPerspectiveDescriptor pers) {
		this.input = input;
		perspective = pers;
	}

	@PostConstruct
	public void setup() {
		try {
			final IEclipseContext windowContext = model.getContext();
			HandlerServiceImpl.push(windowContext.getParent(), null);

			if (getModel().getPersistedState().containsKey(IPreferenceConstants.COOLBAR_VISIBLE)) {
				this.coolBarVisible = Boolean.parseBoolean(getModel().getPersistedState().get(
						IPreferenceConstants.COOLBAR_VISIBLE));
			} else {
				this.coolBarVisible = PrefUtil.getInternalPreferenceStore().getBoolean(
						IPreferenceConstants.COOLBAR_VISIBLE);
				getModel().getPersistedState().put(IPreferenceConstants.COOLBAR_VISIBLE,
						Boolean.toString(this.coolBarVisible));
			}
			if (getModel().getPersistedState().containsKey(
					IPreferenceConstants.PERSPECTIVEBAR_VISIBLE)) {
				this.perspectiveBarVisible = Boolean.parseBoolean(getModel().getPersistedState()
						.get(IPreferenceConstants.PERSPECTIVEBAR_VISIBLE));
			} else {
				this.perspectiveBarVisible = PrefUtil.getInternalPreferenceStore().getBoolean(
						IPreferenceConstants.PERSPECTIVEBAR_VISIBLE);
				getModel().getPersistedState().put(IPreferenceConstants.PERSPECTIVEBAR_VISIBLE,
						Boolean.toString(this.perspectiveBarVisible));
			}

			IServiceLocatorCreator slc = workbench
					.getService(IServiceLocatorCreator.class);
			this.serviceLocator = (ServiceLocator) slc.createServiceLocator(workbench, null,
					new IDisposable() {
						@Override
						public void dispose() {
							final Shell shell = getShell();
							if (shell != null && !shell.isDisposed()) {
								close();
							}
						}
					}, windowContext);

			windowContext.set(IExtensionTracker.class.getName(), new ContextFunction() {
				@Override
				public Object compute(IEclipseContext context, String contextKey) {
					if (tracker == null) {
						tracker = new UIExtensionTracker(getWorkbench().getDisplay());
					}
					return tracker;
				}
			});

			windowContext.set(IWindowCloseHandler.class.getName(), new IWindowCloseHandler() {
				@Override
				public boolean close(MWindow window) {
					return getWindowAdvisor().preWindowShellClose() && WorkbenchWindow.this.close();
				}
			});

			final ISaveHandler defaultSaveHandler = windowContext.get(ISaveHandler.class);
			final PartServiceSaveHandler localSaveHandler = new PartServiceSaveHandler() {
				@Override
				public Save promptToSave(MPart dirtyPart) {
					Object object = dirtyPart.getObject();
					if (object instanceof CompatibilityPart) {
						IWorkbenchPart part = ((CompatibilityPart) object).getPart();
						if (part instanceof ISaveablePart) {
							if (!((ISaveablePart) part).isSaveOnCloseNeeded())
								return Save.NO;
							return SaveableHelper.savePart((ISaveablePart) part, part,
									WorkbenchWindow.this, true) ? Save.NO : Save.CANCEL;
						}
					}
					return defaultSaveHandler.promptToSave(dirtyPart);
				}

				@Override
				public Save[] promptToSave(Collection<MPart> dirtyParts) {
					LabelProvider labelProvider = new LabelProvider() {
						@Override
						public String getText(Object element) {
							return ((MPart) element).getLocalizedLabel();
						}
					};
					List<MPart> parts = new ArrayList<MPart>(dirtyParts);
					ListSelectionDialog dialog = new ListSelectionDialog(getShell(), parts,
							ArrayContentProvider.getInstance(), labelProvider,
							WorkbenchMessages.EditorManager_saveResourcesMessage);
					dialog.setInitialSelections(parts.toArray());
					dialog.setTitle(WorkbenchMessages.EditorManager_saveResourcesTitle);
					if (dialog.open() == IDialogConstants.CANCEL_ID) {
						return new Save[] { Save.CANCEL };
					}

					Object[] toSave = dialog.getResult();
					Save[] retSaves = new Save[parts.size()];
					Arrays.fill(retSaves, Save.NO);
					for (int i = 0; i < retSaves.length; i++) {
						MPart part = parts.get(i);
						for (Object o : toSave) {
							if (o == part) {
								retSaves[i] = Save.YES;
								break;
							}
						}
					}
					return retSaves;
				}

				@Override
				public boolean save(MPart dirtyPart, boolean confirm) {
					Object object = dirtyPart.getObject();
					if (object instanceof CompatibilityPart) {
						IWorkbenchPart workbenchPart = ((CompatibilityPart) object).getPart();
						if (workbenchPart instanceof ISaveablePart) {
							SaveablesList saveablesList = (SaveablesList) PlatformUI.getWorkbench()
									.getService(ISaveablesLifecycleListener.class);
							Object saveResult = saveablesList.preCloseParts(
									Collections.singletonList((ISaveablePart) workbenchPart), true,
									WorkbenchWindow.this);
							return saveResult != null;
						}
					}
					return super.save(dirtyPart, confirm);
				}

				@Override
				public boolean saveParts(Collection<MPart> dirtyParts, boolean confirm) {
					ArrayList<ISaveablePart> saveables = new ArrayList<ISaveablePart>();
					for (MPart part : dirtyParts) {
						Object object = part.getObject();
						if (object instanceof CompatibilityPart) {
							IWorkbenchPart workbenchPart = ((CompatibilityPart) object).getPart();
							if (workbenchPart instanceof ISaveablePart) {
								saveables.add((ISaveablePart) workbenchPart);
							}
						}
					}
					if (saveables.isEmpty()) {
						return super.saveParts(dirtyParts, confirm);
					}

					SaveablesList saveablesList = (SaveablesList) PlatformUI.getWorkbench()
							.getService(ISaveablesLifecycleListener.class);
					Object saveResult = saveablesList.preCloseParts(saveables, true,
							WorkbenchWindow.this);
					return saveResult != null;
				}
			};
			localSaveHandler.logger = logger;
			windowContext.set(ISaveHandler.class, localSaveHandler);

			windowContext.set(IWorkbenchWindow.class.getName(), this);
			windowContext.set(IPageService.class, this);
			windowContext.set(IPartService.class, partService);

			windowContext.set(ISources.ACTIVE_WORKBENCH_WINDOW_NAME, this);
			windowContext.set(ISources.ACTIVE_WORKBENCH_WINDOW_SHELL_NAME, getShell());
			EContextService cs = (EContextService) windowContext.get(EContextService.class
					.getName());
			cs.activateContext(IContextService.CONTEXT_ID_WINDOW);
			cs.getActiveContextIds();

			initializeDefaultServices();

			cleanLegacyQuickAccessContribution();


			fireWindowOpening();
			configureShell(getShell(), windowContext);

			try {
				page = new WorkbenchPage(this, input);
			} catch (WorkbenchException e) {
				WorkbenchPlugin.log(e);
			}

			ContextInjectionFactory.inject(page, model.getContext());
			windowContext.set(IWorkbenchPage.class, page);

			menuManager.setOverrides(menuOverride);
			((CoolBarToTrimManager) getCoolBarManager2()).setOverrides(toolbarOverride);

			fillActionBars(FILL_ALL_ACTION_BARS);
			firePageOpened();

			populateTopTrimContributions();
			populateBottomTrimContributions();

			modelService.getTrim(model, SideValue.LEFT);
			modelService.getTrim(model, SideValue.RIGHT);

			positionQuickAccess();

			Shell shell = (Shell) model.getWidget();
			if (model.getMainMenu() == null) {
				final MMenu mainMenu = modelService.createModelElement(MMenu.class);
				mainMenu.setElementId("org.eclipse.ui.main.menu"); //$NON-NLS-1$

				final MenuManagerRenderer renderer = (MenuManagerRenderer) rendererFactory
						.getRenderer(mainMenu, null);
				renderer.linkModelToManager(mainMenu, menuManager);
				fill(renderer, mainMenu, menuManager);
				model.setMainMenu(mainMenu);
				final Menu menu = (Menu) engine.createGui(mainMenu, model.getWidget(),
						model.getContext());
				shell.setMenuBar(menu);

				menuUpdater = new Runnable() {
					@Override
					public void run() {
						try {
							if (model.getMainMenu() == null || model.getWidget() == null
									|| menu.isDisposed() || mainMenu.getWidget() == null) {
								return;
							}
							MenuManagerRendererFilter.updateElementVisibility(mainMenu, renderer,
									menuManager, windowContext.getActiveLeaf(), 1, false);
							menuManager.update(true);
						} finally {
							canUpdateMenus = true;
						}
					}
				};

				RunAndTrack menuChangeManager = new RunAndTrack() {
					@Override
					public boolean changed(IEclipseContext context) {
						ExpressionInfo info = new ExpressionInfo();
						IEclipseContext leafContext = windowContext.getActiveLeaf();
						MenuManagerRendererFilter.collectInfo(info, mainMenu, renderer,
								leafContext, true);
						for (String name : info.getAccessedVariableNames()) {
							leafContext.get(name);
						}
						if (canUpdateMenus && workbench.getDisplay() != null) {
							canUpdateMenus = false;
							workbench.getDisplay().asyncExec(menuUpdater);
						}
						return manageChanges;
					}
				};
				windowContext.runAndTrack(menuChangeManager);
			}

			eventBroker.subscribe(UIEvents.UIElement.TOPIC_WIDGET, windowWidgetHandler);

			boolean newWindow = setupPerspectiveStack(windowContext);
			partService.setPage(page);
			page.setPerspective(perspective);
			firePageActivated();

			if (newWindow) {
				page.fireInitialPartVisibilityEvents();
			} else {
				page.updatePerspectiveActionSets();
			}
			updateActionSets();

			IPreferenceStore preferenceStore = PrefUtil.getAPIPreferenceStore();
			boolean enableAnimations = preferenceStore
					.getBoolean(IWorkbenchPreferenceConstants.ENABLE_ANIMATIONS);
			preferenceStore.setValue(IWorkbenchPreferenceConstants.ENABLE_ANIMATIONS, false);

			List<MPerspective> persps = modelService.findElements(model, null, MPerspective.class,
					null);
			if (persps.size() > 1) {
				PrefUtil.getAPIPreferenceStore().setValue(IWorkbenchPreferenceConstants.SHOW_INTRO,
						false);
				PrefUtil.saveAPIPrefs();
			}
			getWindowAdvisor().postWindowCreate();
			getWindowAdvisor().openIntro();

			preferenceStore.setValue(IWorkbenchPreferenceConstants.ENABLE_ANIMATIONS,
					enableAnimations);

			getShell().setData(this);
			trackShellActivation();
		} finally {
			HandlerServiceImpl.pop();
		}
	}

	private void configureShell(Shell shell, IEclipseContext context) {
		String title = getWindowConfigurer().basicGetTitle();
		if (title != null) {
			shell.setText(TextProcessor.process(title, TEXT_DELIMITERS));
		}
		workbench.getHelpSystem().setHelp(shell, IWorkbenchHelpContextIds.WORKBENCH_WINDOW);

		IContextService contextService = context.get(IContextService.class);
		contextService.registerShell(shell, IContextService.TYPE_WINDOW);
		if (model.getContext().get(E4Workbench.NO_SAVED_MODEL_FOUND) != null) {
			Point initialSize = getWindowConfigurer().getInitialSize();
			Rectangle bounds = shell.getBounds();
			bounds.width = initialSize.x;
			bounds.height = initialSize.y;
			shell.setBounds(bounds);
		}
	}
	
	private boolean setupPerspectiveStack(IEclipseContext context) {
		IPerspectiveRegistry registry = getWorkbench().getPerspectiveRegistry();
		String forcedPerspectiveId = (String) context.get(E4Workbench.FORCED_PERSPECTIVE_ID);

		if (forcedPerspectiveId != null) {
			perspective = registry.findPerspectiveWithId(forcedPerspectiveId);
		}

		List<MPerspectiveStack> perspStackList = modelService.findElements(model, null,
				MPerspectiveStack.class, null);
		MPerspective selectedPersp = null;

		if (!perspStackList.isEmpty()) {
			selectedPersp = perspStackList.get(0).getSelectedElement();
		}

		if (forcedPerspectiveId == null && selectedPersp != null) {
			perspective = registry.findPerspectiveWithId(selectedPersp.getElementId());
		}

		if (perspective == null) {
			perspective = registry.findPerspectiveWithId(registry.getDefaultPerspective());
		}

		return selectedPersp == null;
	}

	private boolean manageChanges = true;
	private boolean canUpdateMenus = true;

	void populateTopTrimContributions() {
		getCoolBarManager2().update(true);
		getCoolBarManager2().add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));

		final MTrimBar trimBar = getTopTrim();
		MToolControl spacerControl = (MToolControl) modelService.find(PERSPECTIVE_SPACER_ID, model);
		if (spacerControl == null) {
			spacerControl = modelService.createModelElement(MToolControl.class);
			spacerControl.setElementId(PERSPECTIVE_SPACER_ID);
			spacerControl
					.setContributionURI("bundleclass://org.eclipse.e4.ui.workbench.renderers.swt/org.eclipse.e4.ui.workbench.renderers.swt.LayoutModifierToolControl"); //$NON-NLS-1$
			spacerControl.getTags().add(TrimBarLayout.SPACER);
			spacerControl.getTags().add("SHOW_RESTORE_MENU"); //$NON-NLS-1$
			trimBar.getChildren().add(spacerControl);
		} else {
			if (!spacerControl.getTags().contains("SHOW_RESTORE_MENU")) { //$NON-NLS-1$
				spacerControl.getTags().add("SHOW_RESTORE_MENU"); //$NON-NLS-1$
			}
		}

		MToolControl switcherControl = (MToolControl) modelService.find(
				"PerspectiveSwitcher", model); //$NON-NLS-1$
		if (switcherControl == null && getWindowConfigurer().getShowPerspectiveBar()) {
			switcherControl = modelService.createModelElement(MToolControl.class);
			switcherControl.setToBeRendered(getWindowConfigurer().getShowPerspectiveBar());
			switcherControl.setElementId("PerspectiveSwitcher"); //$NON-NLS-1$
			switcherControl.getTags().add(IPresentationEngine.DRAGGABLE);
			switcherControl.getTags().add("HIDEABLE"); //$NON-NLS-1$
			switcherControl.getTags().add("SHOW_RESTORE_MENU"); //$NON-NLS-1$
			switcherControl
					.setContributionURI("bundleclass://org.eclipse.ui.workbench/org.eclipse.e4.ui.workbench.addons.perspectiveswitcher.PerspectiveSwitcher"); //$NON-NLS-1$
			trimBar.getChildren().add(switcherControl);
		} else if (switcherControl != null) {
			if (!getWindowConfigurer().getShowPerspectiveBar()) {
				trimBar.getChildren().remove(switcherControl);
			} else {
				List<String> tags = switcherControl.getTags();
				if (!tags.contains("HIDEABLE")) { //$NON-NLS-1$
					tags.add("HIDEABLE"); //$NON-NLS-1$
				}
				if (!tags.contains("SHOW_RESTORE_MENU")) { //$NON-NLS-1$
					tags.add("SHOW_RESTORE_MENU"); //$NON-NLS-1$
				}
			}
		}

		updateLayoutDataForContents();
	}

	private void cleanLegacyQuickAccessContribution() {
		for (String quickAccessElementId : QUICK_ACCESS_ELEMENT_IDS) {
			MToolControl legacyElement = (MToolControl) modelService.find(quickAccessElementId,
					model);
			if (legacyElement != null) {
				EcoreUtil.remove((EObject) legacyElement);
			}
		}
	}

	private void positionQuickAccess() {
		for (String quickAccessElementId : QUICK_ACCESS_ELEMENT_IDS) {
			MToolControl quickAccessElement = (MToolControl) modelService.find(
					quickAccessElementId, model);
			if (quickAccessElement != null) {
				moveControl(quickAccessElement.getParent(), quickAccessElement);

				if (QUICK_ACCESS_ID.equals(quickAccessElement.getElementId())) {
					if (model.getTags().contains(QUICK_ACCESS_HIDDEN)) {
						if (!quickAccessElement.getTags().contains(
								IPresentationEngine.HIDDEN_EXPLICITLY)) {
							quickAccessElement.getTags().add(IPresentationEngine.HIDDEN_EXPLICITLY);
						}
					}
				}
			}
		}

	}

	private static final String QUICK_ACCESS_ID = "SearchField"; //$NON-NLS-1$
	private static final String QUICK_ACCESS_HIDDEN = "QUICK_ACCESS_HIDDEN"; //$NON-NLS-1$

	@Inject
	private void hideQuickAccess(
			@Optional @UIEventTopic(UIEvents.ApplicationElement.TOPIC_TAGS) Event event) {
		if (event == null) {
			return;
		}
		Object origin = event.getProperty(UIEvents.EventTags.ELEMENT);
		if (!(origin instanceof MToolControl)) {
			return;
		}
		MToolControl control = (MToolControl) origin;
		if (!QUICK_ACCESS_ID.equals(control.getElementId())) {
			return;
		}
		MWindow myWindow = modelService.getTopLevelWindowFor(control);
		if (myWindow != model) {
			return;
		}
		if (UIEvents.isADD(event)) {
			if (UIEvents.contains(event, UIEvents.EventTags.NEW_VALUE,
					IPresentationEngine.HIDDEN_EXPLICITLY)) {
				if (!model.getTags().contains(QUICK_ACCESS_HIDDEN)) {
					model.getTags().add(QUICK_ACCESS_HIDDEN);
				}
			}
		} else if (UIEvents.isREMOVE(event)) {
			if (UIEvents.contains(event, UIEvents.EventTags.OLD_VALUE,
					IPresentationEngine.HIDDEN_EXPLICITLY)) {
				model.getTags().remove(QUICK_ACCESS_HIDDEN);
			}
		}
	}

	private void moveControl(MElementContainer<MUIElement> elementContainer, MUIElement element) {
		if (element == null || elementContainer == null)
			return;

		PositionInfo positionInfo = findMovePositionInfo(element);

		if (positionInfo != null) {
			List<MUIElement> elements = elementContainer.getChildren();

			if (elements.remove(element)) {

				switch (positionInfo.getPosition()) {
				case LAST:
					elements.add(element);
					break;

				case FIRST:
					elements.add(0, element);
					break;

				case INDEX:
					int index = positionInfo.getPositionReferenceAsInteger();
					if (index >= 0 && index < elements.size()) {
						elements.add(index, element);
					} else {
						elements.add(element);
					}
					break;

				case BEFORE:
				case AFTER:
					int idx = indexOfElementWithID(elements, positionInfo.getPositionReference());
					if (idx < 0) {
						elements.add(element);
					} else {
						if (positionInfo.getPosition() == Position.AFTER) {
							idx++;
						}

						if (idx < elements.size()) {
							elements.add(idx, element);
						} else {
							elements.add(element);
						}
					}
					break;

				default:
					WorkbenchPlugin.log("Can't position control '" + element.getElementId() //$NON-NLS-1$
							+ "' because of the unknown position type '" //$NON-NLS-1$
							+ positionInfo.getPosition() + "'!"); //$NON-NLS-1$
				}
			}
		}
	}

	private int indexOfElementWithID(List<MUIElement> elements, String id) {
		if (elements == null || id == null)
			return -1;

		int index = 0;
		for (MUIElement element : elements) {
			if (id.equals(element.getElementId())) {
				return index;
			}
			index++;
		}

		return -1;
	}

	private PositionInfo findMovePositionInfo(MUIElement element) {
		if (element != null) {
			for (String tag : element.getTags()) {
				if (tag.startsWith(MOVE_TAG)) {
					return PositionInfo.parse(tag.substring(MOVE_TAG.length()));
				}
			}
		}

		return null;
	}

	private void populateStandardTrim(MTrimBar bottomTrim) {
		MToolControl slElement = (MToolControl) modelService.find(
				"org.eclipse.ui.StatusLine", model); //$NON-NLS-1$
		if (slElement == null) {
			slElement = modelService.createModelElement(MToolControl.class);
			slElement.setElementId("org.eclipse.ui.StatusLine"); //$NON-NLS-1$
			slElement
					.setContributionURI("bundleclass://org.eclipse.ui.workbench/org.eclipse.ui.internal.StandardTrim"); //$NON-NLS-1$
			bottomTrim.getChildren().add(slElement);
		}
		slElement.setToBeRendered(statusLineVisible);
		slElement.getTags().add(TrimBarLayout.SPACER);

		MToolControl hsElement = (MToolControl) modelService.find(
				"org.eclipse.ui.HeapStatus", model); //$NON-NLS-1$
		if (hsElement == null) {
			hsElement = modelService.createModelElement(MToolControl.class);
			hsElement.setElementId("org.eclipse.ui.HeapStatus"); //$NON-NLS-1$
			hsElement
					.setContributionURI("bundleclass://org.eclipse.ui.workbench/org.eclipse.ui.internal.StandardTrim"); //$NON-NLS-1$
			hsElement.getTags().add(IPresentationEngine.DRAGGABLE);
			bottomTrim.getChildren().add(hsElement);
		}
		hsElement.setToBeRendered(getShowHeapStatus());

		MToolControl pbElement = (MToolControl) modelService.find(
				"org.eclipse.ui.ProgressBar", model); //$NON-NLS-1$
		if (pbElement == null) {
			pbElement = modelService.createModelElement(MToolControl.class);
			pbElement.setElementId("org.eclipse.ui.ProgressBar"); //$NON-NLS-1$
			pbElement.getTags().add(IPresentationEngine.DRAGGABLE);
			pbElement
					.setContributionURI("bundleclass://org.eclipse.ui.workbench/org.eclipse.ui.internal.StandardTrim"); //$NON-NLS-1$
			bottomTrim.getChildren().add(pbElement);
		}
		pbElement.setToBeRendered(getWindowConfigurer().getShowProgressIndicator());
	}

	private void populateTrimContributions(MTrimBar bottomTrim) {
		IConfigurationElement[] exts = extensionRegistry
				.getConfigurationElementsFor("org.eclipse.ui.menus"); //$NON-NLS-1$
		List<IConfigurationElement> items = new ArrayList<IConfigurationElement>();
		for (IConfigurationElement ice : exts) {
			if ("group".equals(ice.getName()) || "widget".equals(ice.getName())) { //$NON-NLS-1$ //$NON-NLS-2$
				items.add(ice);
			}
		}

		if (items.size() == 0)
			return;

		List<IConfigurationElement> handledElements = new ArrayList<IConfigurationElement>();
		handledElements.add(items.get(0)); // Hack!! startup seeding
		MUIElement createdTrim = null;
		while (items.size() > 0 && handledElements.size() > 0) {
			handledElements.clear();

			for (IConfigurationElement item : items) {
				String id = item.getAttribute("id"); //$NON-NLS-1$
				String classSpec = item.getAttribute("class"); //$NON-NLS-1$
				IConfigurationElement[] locs = item.getChildren("location"); //$NON-NLS-1$
				for (IConfigurationElement loc : locs) {
					IConfigurationElement[] bars = loc.getChildren("bar"); //$NON-NLS-1$
					if (bars.length > 0) {
						IConfigurationElement bar = bars[0];
						boolean isTrim = "trim".equals(bar.getAttribute("type")); //$NON-NLS-1$//$NON-NLS-2$
						if (isTrim) {
							String path = bar.getAttribute("path"); //$NON-NLS-1$
							if (path != null && path.length() > 0) {
								createdTrim = addTrimElement(bottomTrim, item, id, false, path,
										classSpec);
							} else {
								IConfigurationElement[] orders = loc.getChildren("order"); //$NON-NLS-1$
								if (orders.length > 0) {
									boolean isBefore = "before".equals(orders[0].getAttribute("position")); //$NON-NLS-1$//$NON-NLS-2$
									String relTo = orders[0].getAttribute("relativeTo"); //$NON-NLS-1$
									if ("status".equals(relTo)) //$NON-NLS-1$
										relTo = "org.eclipse.ui.StatusLine"; //$NON-NLS-1$

									createdTrim = addTrimElement(bottomTrim, item, id, isBefore,
											relTo, classSpec);
								}
							}

							if (createdTrim != null) {
								handledElements.add(item);
							}
						}
					}
				}
			}

			items.removeAll(handledElements);
		}
	}

	private MToolControl addTrimElement(MTrimBar bottomTrim, IConfigurationElement ice, String id,
			boolean isBefore, String relTo, String classSpec) {
		MUIElement existingTrim = modelService.find(id, bottomTrim);
		if (existingTrim != null) {
			iceMap.put((MToolControl) existingTrim, ice);
			return (MToolControl) existingTrim;
		}

		int insertIndex = bottomTrim.getChildren().size();
		if (relTo != null) {
			MUIElement foundRel = modelService.find(relTo, bottomTrim);
			if (foundRel == null)
				return null;
			insertIndex = bottomTrim.getChildren().indexOf(foundRel);
			if (!isBefore)
				insertIndex++;
		}

		MToolControl newTrimElement = modelService.createModelElement(MToolControl.class);
		newTrimElement.setElementId(id);
		newTrimElement.setToBeRendered(classSpec != null);
		if (classSpec != null) {
			newTrimElement
					.setContributionURI("bundleclass://org.eclipse.ui.workbench/org.eclipse.ui.internal.LegacyTrim"); //$NON-NLS-1$
		}
		newTrimElement.setContributorURI(URIHelper.constructPlatformURI(ice.getContributor()));

		iceMap.put(newTrimElement, ice);
		bottomTrim.getChildren().add(insertIndex, newTrimElement);

		return newTrimElement;
	}

	void populateBottomTrimContributions() {
		MTrimBar bottomTrim = modelService.getTrim(model, SideValue.BOTTOM);

		populateStandardTrim(bottomTrim);
		populateTrimContributions(bottomTrim);
	}

	public MTrimBar getTopTrim() {
		List<MTrimBar> trimBars = model.getTrimBars();
		for (MTrimBar bar : trimBars) {
			if (MAIN_TOOLBAR_ID.equals(bar.getElementId())) {
				return bar;
			}
		}
		return null;
	}

	private void fill(MenuManagerRenderer renderer, MMenu menu, IMenuManager manager) {
		for (IContributionItem item : manager.getItems()) {
			if (item instanceof MenuManager) {
				MenuManager menuManager = (MenuManager) item;
				MMenu subMenu = MenuHelper.createMenu(menuManager);
				if (subMenu != null) {
					renderer.linkModelToContribution(subMenu, item);
					renderer.linkModelToManager(subMenu, menuManager);
					fill(renderer, subMenu, menuManager);
					menu.getChildren().add(subMenu);
				}
			} else if (item instanceof CommandContributionItem) {
				CommandContributionItem cci = (CommandContributionItem) item;
				MMenuItem menuItem = MenuHelper.createItem(application, cci);
				manager.remove(item);
				if (menuItem != null) {
					menu.getChildren().add(menuItem);
				}
			} else if (item instanceof AbstractGroupMarker) {
				MMenuSeparator separator = modelService.createModelElement(MMenuSeparator.class);
				separator.setVisible(item.isVisible());
				separator.setElementId(item.getId());
				if (item instanceof GroupMarker) {
					separator.getTags().add(MenuManagerRenderer.GROUP_MARKER);
				}
				menu.getChildren().add(separator);
				manager.remove(item);
			} else {
				MMenuItem menuItem = OpaqueElementUtil.createOpaqueMenuItem();
				menuItem.setElementId(item.getId());
				menuItem.setVisible(item.isVisible());
				OpaqueElementUtil.setOpaqueItem(menuItem, item);
				menu.getChildren().add(menuItem);
				renderer.linkModelToContribution(menuItem, item);
			}
		}
	}

	public static String getId(IConfigurationElement element) {
		String id = element.getAttribute(IWorkbenchRegistryConstants.ATT_ID);

		if (id == null || id.length() == 0) {
			id = getCommandId(element);
		}
		if (id == null || id.length() == 0) {
			id = element.toString();
		}

		return id;
	}

	public static String getCommandId(IConfigurationElement element) {
		return element.getAttribute(IWorkbenchRegistryConstants.ATT_COMMAND_ID);
	}

	public static String getActionSetCommandId(IConfigurationElement element) {
		String id = getDefinitionId(element);
		if (id != null) {
			return id;
		}
		id = getId(element);
		String actionSetId = null;
		Object obj = element.getParent();
		while (obj instanceof IConfigurationElement && actionSetId == null) {
			IConfigurationElement parent = (IConfigurationElement) obj;
			if (parent.getName().equals(IWorkbenchRegistryConstants.TAG_ACTION_SET)) {
				actionSetId = getId(parent);
			}
			obj = parent.getParent();
		}
		return ACTION_SET_CMD_PREFIX + actionSetId + '/' + id;
	}

	public static String getDefinitionId(IConfigurationElement element) {
		return element.getAttribute(IWorkbenchRegistryConstants.ATT_DEFINITION_ID);
	}

	public static boolean getRetarget(IConfigurationElement element) {
		String r = element.getAttribute(IWorkbenchRegistryConstants.ATT_RETARGET);
		return Boolean.valueOf(r);
	}

	protected int perspectiveBarStyle() {
		return SWT.FLAT | SWT.WRAP | SWT.RIGHT | SWT.HORIZONTAL;
	}

	private boolean coolBarVisible = true;

	private boolean perspectiveBarVisible = true;

	private boolean fastViewBarVisible = true;

	private boolean statusLineVisible = true;

	private Map<String, ActionHandler> globalActionHandlersByCommandId = new HashMap<String, ActionHandler>();

	private List<IHandlerActivation> handlerActivations = new ArrayList<IHandlerActivation>();

	private int largeUpdates = 0;

	private IExtensionTracker tracker;

	private void firePageClosed() {
		pageListeners.firePageClosed(page);
	}

	private void firePageOpened() {
		pageListeners.firePageOpened(page);
	}

	private void firePageActivated() {
		pageListeners.firePageActivated(page);
	}

	void registerGlobalAction(IAction globalAction) {
		String commandId = globalAction.getActionDefinitionId();

		if (commandId != null) {
			final Object value = globalActionHandlersByCommandId.remove(commandId);
			if (value instanceof ActionHandler) {
				final ActionHandler handler = (ActionHandler) value;
				handler.dispose();
			}

			if (globalAction instanceof CommandAction) {
				final String actionId = globalAction.getId();
				if (actionId != null) {
					final IActionCommandMappingService mappingService = (IActionCommandMappingService) serviceLocator
							.getService(IActionCommandMappingService.class);
					mappingService.map(actionId, commandId);
				}
			} else {
				globalActionHandlersByCommandId.put(commandId, new ActionHandler(globalAction));
			}
		}

		submitGlobalActions();
	}

	void submitGlobalActions() {
		final IHandlerService handlerService = (IHandlerService) getService(IHandlerService.class);

		Map<String, ActionHandler> handlersByCommandId = new HashMap<String, ActionHandler>();
		handlersByCommandId.putAll(globalActionHandlersByCommandId);

		List<IHandlerActivation> newHandlers = new ArrayList<IHandlerActivation>(
				handlersByCommandId.size());

		Iterator<IHandlerActivation> existingIter = handlerActivations.iterator();
		while (existingIter.hasNext()) {
			IHandlerActivation next = existingIter.next();

			String cmdId = next.getCommandId();

			Object handler = handlersByCommandId.get(cmdId);
			if (handler == next.getHandler()) {
				handlersByCommandId.remove(cmdId);
				newHandlers.add(next);
			} else {
				handlerService.deactivateHandler(next);
			}
		}

		final Shell shell = getShell();
		if (shell != null) {
			final Expression expression = new ActiveShellExpression(shell);
			for (Iterator<Entry<String, ActionHandler>> iterator = handlersByCommandId.entrySet()
					.iterator(); iterator.hasNext();) {
				Entry<String, ActionHandler> entry = iterator.next();
				String commandId = entry.getKey();
				IHandler handler = entry.getValue();
				newHandlers.add(handlerService.activateHandler(commandId, handler, expression));
			}
		}

		handlerActivations = newHandlers;
	}

	public void addPropertyChangeListener(IPropertyChangeListener listener) {
		genericPropertyListeners.add(listener);
	}

	public void removePropertyChangeListener(IPropertyChangeListener listener) {
		genericPropertyListeners.remove(listener);
	}

	private void firePropertyChanged(final String property, final Object oldValue,
			final Object newValue) {
		PropertyChangeEvent event = new PropertyChangeEvent(this, property, oldValue, newValue);
		Object[] listeners = genericPropertyListeners.getListeners();
		for (int i = 0; i < listeners.length; i++) {
			IPropertyChangeListener listener = (IPropertyChangeListener) listeners[i];
			listener.propertyChange(event);
		}
	}

	@Override
	public void addPageListener(IPageListener l) {
		pageListeners.addPageListener(l);
	}

	@Override
	public void addPerspectiveListener(org.eclipse.ui.IPerspectiveListener l) {
		perspectiveListeners.addPerspectiveListener(l);
	}

	private boolean busyClose(boolean remove) {
		if (closing)
			return false;
		boolean windowClosed = false;

		updateDisabled = true;

		try {
			IWorkbenchPage activePage = getActivePage();
			if (activePage != null) {
				WorkbenchPartReference ref = (WorkbenchPartReference) activePage
						.getActivePartReference();
				if (ref != null) {
					ref.getModel().getTags().add(EPartService.ACTIVE_ON_CLOSE_TAG);
				}
			}

			Workbench workbench = getWorkbenchImpl();
			int count = workbench.getWorkbenchWindowCount();
			if (!workbench.isStarting() && !workbench.isClosing() && count <= 1
					&& workbench.getWorkbenchConfigurer().getExitOnLastWindowClose()) {
				windowClosed = workbench.close();
			} else {
				if (okToClose()) {
					closing = true;
					windowClosed = hardClose(remove);
				}
			}
		} finally {
			if (!windowClosed) {
				closing = false;
				updateDisabled = false;
			}
		}

		if (windowClosed && tracker != null) {
			tracker.close();
		}

		return windowClosed;
	}

	@Override
	public Shell getShell() {
		return (Shell) model.getWidget();
	}

	public boolean close(final boolean remove) {
		final boolean[] ret = new boolean[1];
		BusyIndicator.showWhile(null, new Runnable() {
			@Override
			public void run() {
				ret[0] = busyClose(remove);
			}
		});
		return ret[0];
	}

	@Override
	public boolean close() {
		return close(true);
	}

	protected boolean isClosing() {
		return closing || getWorkbenchImpl().isClosing();
	}

	private void fireWindowOpening() {
		getWindowAdvisor().preWindowOpen();
	}

	void fireWindowOpened() {
		getWindowAdvisor().postWindowOpen();
	}

	void fireWindowRestored() throws WorkbenchException {
		StartupThreading.runWithWorkbenchExceptions(new StartupRunnable() {
			@Override
			public void runWithException() throws Throwable {
				getWindowAdvisor().postWindowRestore();
			}
		});
	}

	private void fireWindowClosed() {
		getWindowAdvisor().postWindowClose();
		getWorkbenchImpl().fireWindowClosed(this);
	}

	private void allowUpdates(IMenuManager menuManager) {
		menuManager.markDirty();
		final IContributionItem[] items = menuManager.getItems();
		for (int i = 0; i < items.length; i++) {
			if (items[i] instanceof IMenuManager) {
				allowUpdates((IMenuManager) items[i]);
			} else if (items[i] instanceof SubContributionItem) {
				final IContributionItem innerItem = ((SubContributionItem) items[i]).getInnerItem();
				if (innerItem instanceof IMenuManager) {
					allowUpdates((IMenuManager) innerItem);
				}
			}
		}
	}

	void firePerspectiveActivated(IWorkbenchPage page, IPerspectiveDescriptor perspective) {
		IMenuManager windowManager = ((WorkbenchPage) page).getActionBars().getMenuManager();
		allowUpdates(windowManager);
		windowManager.update(false);

		UIListenerLogging.logPerspectiveEvent(this, page, perspective,
				UIListenerLogging.PLE_PERSP_ACTIVATED);
		perspectiveListeners.firePerspectiveActivated(page, perspective);
	}

	void firePerspectivePreDeactivate(IWorkbenchPage page, IPerspectiveDescriptor perspective) {
		UIListenerLogging.logPerspectiveEvent(this, page, perspective,
				UIListenerLogging.PLE_PERSP_PRE_DEACTIVATE);
		perspectiveListeners.firePerspectivePreDeactivate(page, perspective);
	}

	void firePerspectiveDeactivated(IWorkbenchPage page, IPerspectiveDescriptor perspective) {
		UIListenerLogging.logPerspectiveEvent(this, page, perspective,
				UIListenerLogging.PLE_PERSP_DEACTIVATED);
		perspectiveListeners.firePerspectiveDeactivated(page, perspective);
	}

	public void firePerspectiveChanged(IWorkbenchPage page, IPerspectiveDescriptor perspective,
			String changeId) {
		if (perspective != null) {
			UIListenerLogging.logPerspectiveChangedEvent(this, page, perspective, null, changeId);
			perspectiveListeners.firePerspectiveChanged(page, perspective, changeId);
		}
	}

	public void firePerspectiveChanged(IWorkbenchPage page, IPerspectiveDescriptor perspective,
			IWorkbenchPartReference partRef, String changeId) {
		if (perspective != null) {
			UIListenerLogging
					.logPerspectiveChangedEvent(this, page, perspective, partRef, changeId);
			perspectiveListeners.firePerspectiveChanged(page, perspective, partRef, changeId);
		}
	}

	void firePerspectiveClosed(IWorkbenchPage page, IPerspectiveDescriptor perspective) {
		UIListenerLogging.logPerspectiveEvent(this, page, perspective,
				UIListenerLogging.PLE_PERSP_CLOSED);
		perspectiveListeners.firePerspectiveClosed(page, perspective);
	}

	void firePerspectiveOpened(IWorkbenchPage page, IPerspectiveDescriptor perspective) {
		UIListenerLogging.logPerspectiveEvent(this, page, perspective,
				UIListenerLogging.PLE_PERSP_OPENED);
		perspectiveListeners.firePerspectiveOpened(page, perspective);
	}

	void firePerspectiveSavedAs(IWorkbenchPage page, IPerspectiveDescriptor oldPerspective,
			IPerspectiveDescriptor newPerspective) {
		UIListenerLogging.logPerspectiveSavedAs(this, page, oldPerspective, newPerspective);
		perspectiveListeners.firePerspectiveSavedAs(page, oldPerspective, newPerspective);
	}

	public WWinActionBars getActionBars() {
		if (actionBars == null) {
			actionBars = new WWinActionBars(this);
		}
		return actionBars;
	}

	@Override
	public IWorkbenchPage getActivePage() {
		return page;
	}

	@Override
	public IWorkbenchPage[] getPages() {
		return page == null ? new IWorkbenchPage[0] : new IWorkbenchPage[] { page };
	}

	@Override
	public IPartService getPartService() {
		return partService;
	}

	protected Layout getLayout() {
		return null;
	}

	@Override
	public ISelectionService getSelectionService() {
		return selectionService;
	}

	public boolean getShellActivated() {
		return shellActivated;
	}

	@Override
	public IWorkbench getWorkbench() {
		return PlatformUI.getWorkbench();
	}

	private void hideNonRestorableViews() {
		List<MPart> sharedPartsToRemove = new ArrayList<MPart>();
		List<MPlaceholder> phList = modelService
				.findElements(model, null, MPlaceholder.class, null);
		for (MPlaceholder ph : phList) {
			if (!(ph.getRef() instanceof MPart))
				continue;

			String partId = ph.getElementId();

			int colonIndex = partId.indexOf(':');
			String descId = colonIndex == -1 ? partId : partId.substring(0, colonIndex);
			String secondaryId = colonIndex == -1 ? null : partId.substring(colonIndex + 1);
			IViewDescriptor regEntry = ((Workbench) workbench).getViewRegistry().find(descId);
			if (regEntry != null && !regEntry.isRestorable() && !("*".equals(secondaryId))) { //$NON-NLS-1$
				MElementContainer<MUIElement> phParent = ph.getParent();
				if (colonIndex != -1) {
					if (!sharedPartsToRemove.contains(ph.getRef()))
						sharedPartsToRemove.add((MPart) ph.getRef());
					ph.getParent().getChildren().remove(ph);
				} else if (ph.isToBeRendered()) {
					ph.setToBeRendered(false);
				}

				int vc = modelService.countRenderableChildren(phParent);
				if (vc == 0) {
					phParent.setToBeRendered(false);
				}
			}
		}

		List<MUIElement> seList = model.getSharedElements();
		for (MPart partToRemove : sharedPartsToRemove) {
			seList.remove(partToRemove);
		}
	}

	private boolean hardClose(boolean remove) {
		try {
			if (!remove) {
				hideNonRestorableViews();
			}

			final IWorkbench workbench = getWorkbench();
			final IHandlerService handlerService = workbench
					.getService(IHandlerService.class);
			handlerService.deactivateHandlers(handlerActivations);
			final Iterator<IHandlerActivation> activationItr = handlerActivations.iterator();
			while (activationItr.hasNext()) {
				final IHandlerActivation activation = activationItr.next();
				activation.getHandler().dispose();
			}
			handlerActivations.clear();
			globalActionHandlersByCommandId.clear();

			final IContextService contextService = workbench
					.getService(IContextService.class);
			contextService.unregisterShell(getShell());


			getActionBarAdvisor().dispose();
			getWindowAdvisor().dispose();
			coolbarToTrim.dispose();

			progressRegion = null;

			MWindow window = model;
			engine.removeGui(model);

			MElementContainer<MUIElement> parent = window.getParent();
			if (remove) {
				parent.getChildren().remove(window);

				if (parent.getSelectedElement() == window) {
					if (!parent.getChildren().isEmpty()) {
						parent.setSelectedElement(parent.getChildren().get(0));
					}
				}
			}
			if (getActivePage() != null) {
				firePageClosed();
			}
			fireWindowClosed();
		} finally {

			try {
				serviceLocator.dispose();
			} catch (Exception ex) {
				WorkbenchPlugin.log(ex);
			}
			menuRestrictions.clear();
		}
		return true;
	}

	@Override
	public boolean isApplicationMenu(String menuID) {
		return getActionBarAdvisor().isApplicationMenu(menuID);
	}

	boolean isWorkbenchCoolItemId(String id) {
		return windowConfigurer.containsCoolItem(id);
	}

	private boolean okToClose() {
		if (!getWorkbenchImpl().isClosing()) {
			IWorkbenchPage page = getActivePage();
			if (page != null) {
				return ((WorkbenchPage) page).saveAllEditors(true, true, true);
			}
		}
		return true;
	}

	@Override
	public IWorkbenchPage openPage(final String perspectiveId, final IAdaptable input)
			throws WorkbenchException {
		final Object[] result = new Object[1];
		BusyIndicator.showWhile(null, new Runnable() {
			@Override
			public void run() {
				try {
					result[0] = busyOpenPage(perspectiveId, input);
				} catch (WorkbenchException e) {
					result[0] = e;
				}
			}
		});

		if (result[0] instanceof IWorkbenchPage) {
			return (IWorkbenchPage) result[0];
		} else if (result[0] instanceof WorkbenchException) {
			throw (WorkbenchException) result[0];
		} else {
			throw new WorkbenchException(WorkbenchMessages.WorkbenchWindow_exceptionMessage);
		}
	}

	private IWorkbenchPage busyOpenPage(String perspectiveId, IAdaptable input)
			throws WorkbenchException {
		IPerspectiveDescriptor descriptor = workbench.getPerspectiveRegistry()
				.findPerspectiveWithId(perspectiveId);
		if (descriptor == null) {
			throw new WorkbenchException(NLS.bind(
					WorkbenchMessages.WorkbenchPage_ErrorCreatingPerspective, perspectiveId));
		}

		if (page == null) {
			page = new WorkbenchPage(this, input);
			model.getContext().set(IWorkbenchPage.class.getName(), page);

			try {
				ContextInjectionFactory.inject(page, model.getContext());
			} catch (InjectionException e) {
				throw new WorkbenchException(e.getMessage(), e);
			}

			firePageOpened();

			partService.setPage(page);
		} else {
			IWorkbenchWindow window = getWorkbench().openWorkbenchWindow(perspectiveId, input);
			return window.getActivePage();
		}

		perspective = descriptor;
		page.setPerspective(perspective);
		firePageActivated();

		return page;
	}

	@Override
	public IWorkbenchPage openPage(IAdaptable input) throws WorkbenchException {
		return openPage(workbench.getPerspectiveRegistry().getDefaultPerspective(), input);
	}

	@Override
	public void removePageListener(IPageListener l) {
		pageListeners.removePageListener(l);
	}

	@Override
	public void removePerspectiveListener(org.eclipse.ui.IPerspectiveListener l) {
		perspectiveListeners.removePerspectiveListener(l);
	}

	private void disableControl(Control ctrl, List<Control> toEnable) {
		if (ctrl != null && !ctrl.isDisposed() && ctrl.isEnabled()) {
			ctrl.setEnabled(false);
			toEnable.add(ctrl);
		}
	}

	@Override
	public void run(final boolean fork, boolean cancelable, final IRunnableWithProgress runnable)
			throws InvocationTargetException, InterruptedException {
		final StatusLineManager manager = getStatusLineManager();

		boolean progressHack = manager.getControl() == null;
		if (manager == null || progressHack) {
			runnable.run(new NullProgressMonitor());
		} else {
			EPartService partService = model.getContext().get(EPartService.class);
			final MPart curActive = partService.getActivePart();
			boolean wasCancelEnabled = manager.isCancelEnabled();
			boolean enableMainMenu = false;
			
			IBindingService bs = model.getContext().get(IBindingService.class);
			boolean keyFilterEnabled = bs.isKeyFilterEnabled();
			List<Control> toEnable = new ArrayList<Control>();
			Shell theShell = getShell();
			Display display = theShell.getDisplay();
			Control currentFocus = display.getFocusControl();

			try {
				Menu mainMenu = (Menu) model.getMainMenu().getWidget();
				if (mainMenu != null && !mainMenu.isDisposed() && mainMenu.isEnabled()) {
					mainMenu.setEnabled(false);
					enableMainMenu = true;
				}

				if (keyFilterEnabled)
					bs.setKeyFilterEnabled(false);
				
				for (Shell childShell : display.getShells()) {
					if (childShell != theShell) {
						disableControl(childShell, toEnable);
					}
				}
				

				TrimmedPartLayout tpl = (TrimmedPartLayout) getShell().getLayout();
				disableControl(tpl.clientArea, toEnable);
				disableControl(tpl.top, toEnable);
				disableControl(tpl.left, toEnable);
				disableControl(tpl.right, toEnable);
				
				if (tpl.bottom != null && !tpl.bottom.isDisposed() && tpl.bottom.isEnabled()) {
					MUIElement statusLine = modelService.find("org.eclipse.ui.StatusLine", model); //$NON-NLS-1$
					Object slCtrl = statusLine != null ? statusLine.getWidget() : null;
					for (Control bottomCtrl : tpl.bottom.getChildren()) {
						if (bottomCtrl != slCtrl)
							disableControl(bottomCtrl, toEnable);
					}
				}

				manager.setCancelEnabled(cancelable);

				final InvocationTargetException[] ite = new InvocationTargetException[1];
				final InterruptedException[] ie = new InterruptedException[1];

				BusyIndicator.showWhile(getShell().getDisplay(), new Runnable() {
					@Override
					public void run() {
						try {
							ModalContext.run(runnable, fork, manager.getProgressMonitor(),
									getShell().getDisplay());
						} catch (InvocationTargetException e) {
							ite[0] = e;
						} catch (InterruptedException e) {
							ie[0] = e;
						} finally {
							manager.getProgressMonitor().done();
						}
					}
				});

				if (ite[0] != null) {
					throw ite[0];
				} else if (ie[0] != null) {
					throw ie[0];
				}
			} finally {
				manager.setCancelEnabled(wasCancelEnabled);

				if (enableMainMenu) {
					Menu mainMenu = (Menu) model.getMainMenu().getWidget();
					mainMenu.setEnabled(true);
				}
				
				if (keyFilterEnabled)
					bs.setKeyFilterEnabled(true);

				for (Control ctrl : toEnable) {
					if (!ctrl.isDisposed() && !ctrl.isEnabled())
						ctrl.setEnabled(true);
				}

				MPart activePart = partService.getActivePart();
				if (curActive != activePart && activePart != null) {
					engine.focusGui(activePart);
				} else if (currentFocus != null && !currentFocus.isDisposed()) {
					currentFocus.forceFocus();
				}
			}
		}
	}

	@Override
	public void setActivePage(final IWorkbenchPage in) {
		if (getActivePage() != in) {
			if (in == null) {
				firePageClosed();
			}

			page = (WorkbenchPage) in;
			model.getContext().set(IWorkbenchPage.class, page);
			partService.setPage(page);
			updateActionSets();
		}
	}

	private Set<Object> menuRestrictions = new HashSet<Object>();

	private Boolean valueOf(boolean result) {
		return result ? Boolean.TRUE : Boolean.FALSE;
	}

	public Set<Object> getMenuRestrictions() {
		return menuRestrictions;
	}

	void liftRestrictions() {
		if (menuRestrictions.isEmpty()) {
			return;
		}
		EvaluationReference[] refs = menuRestrictions
				.toArray(new EvaluationReference[menuRestrictions.size()]);
		IEvaluationService es = (IEvaluationService) serviceLocator
				.getService(IEvaluationService.class);
		IEvaluationContext currentState = es.getCurrentState();
		boolean changeDetected = false;
		for (int i = 0; i < refs.length; i++) {
			EvaluationReference reference = refs[i];
			reference.setPostingChanges(true);

			boolean os = reference.evaluate(currentState);
			reference.clearResult();
			boolean ns = reference.evaluate(currentState);
			if (os != ns) {
				changeDetected = true;
				reference.getListener().propertyChange(
						new PropertyChangeEvent(reference, reference.getProperty(), valueOf(os),
								valueOf(ns)));
			}
		}
		if (changeDetected) {
			IMenuService ms = getWorkbench().getService(IMenuService.class);
			if (ms instanceof WorkbenchMenuService) {
				((WorkbenchMenuService) ms).updateManagers();
			}
		}
	}

	void imposeRestrictions() {
		Iterator<?> i = menuRestrictions.iterator();
		while (i.hasNext()) {
			EvaluationReference ref = (EvaluationReference) i.next();
			ref.setPostingChanges(false);
		}
	}

	private void trackShellActivation() {
		getShell().addShellListener(new ShellAdapter() {
			@Override
			public void shellActivated(ShellEvent event) {
				shellActivated = true;
				serviceLocator.activate();
				if (getActivePage() != null) {
					getWorkbenchImpl().fireWindowActivated(WorkbenchWindow.this);
				}
				liftRestrictions();
			}

			@Override
			public void shellDeactivated(ShellEvent event) {
				shellActivated = false;
				imposeRestrictions();
				serviceLocator.deactivate();
				if (getActivePage() != null) {
					getWorkbenchImpl().fireWindowDeactivated(WorkbenchWindow.this);
				}
			}
		});
	}

	public void updateActionBars() {
		if (getShell() == null || getShell().isDisposed() || updateDisabled || updatesDeferred()) {
			return;
		}
		getMenuBarManager().update(false);

		try {
			getShell().setLayoutDeferred(true);
			eventBroker.send(UIEvents.REQUEST_ENABLEMENT_UPDATE_TOPIC, UIEvents.ALL_ELEMENT_ID);
			getCoolBarManager2().update(false);
		} finally {
			getShell().setLayoutDeferred(false);
		}

		getStatusLineManager().update(false);
	}

	private boolean updatesDeferred() {
		return largeUpdates > 0;
	}

	public final void largeUpdateStart() {
		largeUpdates++;
	}

	public final void largeUpdateEnd() {
		if (--largeUpdates == 0) {
			updateActionBars();
		}
	}

	public void updateActionSets() {
		if (updateDisabled) {
			return;
		}

		WorkbenchPage currentPage = (WorkbenchPage) getActivePage();
		if (currentPage == null) {
			getActionPresentation().clearActionSets();
		} else {
			ICoolBarManager2 coolBarManager = (ICoolBarManager2) getCoolBarManager2();
			if (coolBarManager != null) {
				coolBarManager.refresh();
			}
			getActionPresentation().setActionSets(currentPage.getActionSets());
		}
		fireActionSetsChanged();
		updateActionBars();

		String path = IWorkbenchActionConstants.M_WINDOW + IWorkbenchActionConstants.SEP
				+ IWorkbenchActionConstants.M_LAUNCH;
		IMenuManager manager = getMenuBarManager().findMenuUsingPath(path);
		IContributionItem item = getMenuBarManager().findUsingPath(path);

		if (manager == null || item == null) {
			return;
		}
		item.setVisible(manager.getItems().length >= 2);
	}

	private ListenerList actionSetListeners = null;

	private ListenerList backgroundSaveListeners = new ListenerList(ListenerList.IDENTITY);

	private ISelectionService selectionService;

	private ITrimManager trimManager;

	private ActionPresentation actionPresentation;

	private final void fireActionSetsChanged() {
		if (actionSetListeners != null) {
			final Object[] listeners = actionSetListeners.getListeners();
			for (int i = 0; i < listeners.length; i++) {
				final IActionSetsListener listener = (IActionSetsListener) listeners[i];
				final WorkbenchPage currentPage = (WorkbenchPage) getActivePage();
				final IActionSetDescriptor[] newActionSets;
				if (currentPage == null) {
					newActionSets = null;
				} else {
					newActionSets = currentPage.getActionSets();
				}
				final ActionSetsEvent event = new ActionSetsEvent(newActionSets);
				listener.actionSetsChanged(event);
			}
		}
	}

	final void addActionSetsListener(final IActionSetsListener listener) {
		if (actionSetListeners == null) {
			actionSetListeners = new ListenerList();
		}

		actionSetListeners.add(listener);
	}

	final void removeActionSetsListener(final IActionSetsListener listener) {
		if (actionSetListeners != null) {
			actionSetListeners.remove(listener);
			if (actionSetListeners.isEmpty()) {
				actionSetListeners = null;
			}
		}
	}

	private boolean getShowHeapStatus() {
		return // Show if the preference is set or debug option is on
		PrefUtil.getAPIPreferenceStore().getBoolean(
				IWorkbenchPreferenceConstants.SHOW_MEMORY_MONITOR)
				|| Boolean
						.valueOf(
								Platform.getDebugOption(PlatformUI.PLUGIN_ID
										+ "/perf/showHeapStatus")).booleanValue(); //$NON-NLS-1$
	}

	public void showHeapStatus(boolean show) {
		MUIElement hsElement = modelService.find("org.eclipse.ui.HeapStatus", model); //$NON-NLS-1$
		if (hsElement != null && hsElement.isToBeRendered() != show) {
			hsElement.setToBeRendered(show);
			getShell().layout(null, SWT.ALL | SWT.CHANGED | SWT.DEFER);
		}
	}

	WorkbenchWindowConfigurer getWindowConfigurer() {
		if (windowConfigurer == null) {
			windowConfigurer = new WorkbenchWindowConfigurer(this);
		}
		return windowConfigurer;
	}

	private/* private - DO NOT CHANGE */
	WorkbenchAdvisor getAdvisor() {
		return getWorkbenchImpl().getAdvisor();
	}

	WorkbenchWindowAdvisor getWindowAdvisor() {
		if (windowAdvisor == null) {
			windowAdvisor = getAdvisor().createWorkbenchWindowAdvisor(getWindowConfigurer());
			Assert.isNotNull(windowAdvisor);
		}
		return windowAdvisor;
	}

	private/* private - DO NOT CHANGE */
	ActionBarAdvisor getActionBarAdvisor() {
		if (actionBarAdvisor == null) {
			actionBarAdvisor = getWindowAdvisor().createActionBarAdvisor(
					getWindowConfigurer().getActionBarConfigurer());
			Assert.isNotNull(actionBarAdvisor);
		}
		return actionBarAdvisor;
	}

	private Workbench getWorkbenchImpl() {
		return Workbench.getInstance();
	}

	public void fillActionBars(int flags) {
		Workbench workbench = getWorkbenchImpl();
		workbench.largeUpdateStart();
		try {
			getActionBarAdvisor().fillActionBars(flags);
		} finally {
			workbench.largeUpdateEnd();
		}
	}

	public void fillActionBars(IActionBarConfigurer2 proxyBars, int flags) {
		Assert.isNotNull(proxyBars);
		WorkbenchWindowConfigurer.WindowActionBarConfigurer wab = (WorkbenchWindowConfigurer.WindowActionBarConfigurer) getWindowConfigurer()
				.getActionBarConfigurer();
		wab.setProxy(proxyBars);
		try {
			getActionBarAdvisor().fillActionBars(flags | ActionBarAdvisor.FILL_PROXY);
		} finally {
			wab.setProxy(null);
		}
	}

	public void setCoolBarVisible(boolean visible) {
		boolean oldValue = coolBarVisible;
		coolBarVisible = visible;
		if (oldValue != coolBarVisible) {
			getModel().getPersistedState().put(IPreferenceConstants.COOLBAR_VISIBLE,
					Boolean.toString(visible));
			updateLayoutDataForContents();
			firePropertyChanged(PROP_COOLBAR_VISIBLE, oldValue ? Boolean.TRUE : Boolean.FALSE,
					coolBarVisible ? Boolean.TRUE : Boolean.FALSE);
		}
	}

	public boolean getCoolBarVisible() {
		return getWindowConfigurer().getShowCoolBar() && coolBarVisible;
	}

	public ActionPresentation getActionPresentation() {
		if (actionPresentation == null) {
			actionPresentation = new ActionPresentation(this);
		}
		return actionPresentation;
	}

	public void setPerspectiveBarVisible(boolean visible) {
		boolean oldValue = perspectiveBarVisible;
		perspectiveBarVisible = visible;
		if (oldValue != perspectiveBarVisible) {
			getModel().getPersistedState().put(IPreferenceConstants.PERSPECTIVEBAR_VISIBLE,
					Boolean.toString(visible));
			updateLayoutDataForContents();
			firePropertyChanged(PROP_PERSPECTIVEBAR_VISIBLE, oldValue ? Boolean.TRUE
					: Boolean.FALSE, perspectiveBarVisible ? Boolean.TRUE : Boolean.FALSE);
		}
	}

	public boolean getPerspectiveBarVisible() {
		return getWindowConfigurer().getShowPerspectiveBar() && perspectiveBarVisible;
	}

	public void setFastViewBarVisible(boolean visible) {
		boolean oldValue = fastViewBarVisible;
		fastViewBarVisible = visible;
		if (oldValue != fastViewBarVisible) {

		}
	}

	public boolean getFastViewBarVisible() {
		return fastViewBarVisible;
	}

	public void setStatusLineVisible(boolean visible) {
		boolean oldValue = statusLineVisible;
		statusLineVisible = visible;
		if (oldValue != statusLineVisible) {
			getModel().getPersistedState().put(IPreferenceConstants.PERSPECTIVEBAR_VISIBLE,
					Boolean.toString(visible));
			updateLayoutDataForContents();
			firePropertyChanged(PROP_STATUS_LINE_VISIBLE, oldValue ? Boolean.TRUE : Boolean.FALSE,
					statusLineVisible ? Boolean.TRUE : Boolean.FALSE);
		}
	}

	public boolean getStatusLineVisible() {
		return statusLineVisible;
	}

	public boolean getShowFastViewBars() {
		return getWindowConfigurer().getShowFastViewBars();
	}

	protected boolean showTopSeperator() {
		return false;
	}

	public ProgressRegion getProgressRegion() {
		return progressRegion;
	}

	@Override
	public IExtensionTracker getExtensionTracker() {
		return (IExtensionTracker) model.getContext().get(IExtensionTracker.class.getName());
	}

	IAdaptable getDefaultPageInput() {
		return getWorkbenchImpl().getDefaultPageInput();
	}

	public ITrimManager getTrimManager() {
		if (trimManager == null) {
			trimManager = new ITrimManager() {
				@Override
				public void addTrim(int areaId, IWindowTrim trim) {
				}

				@Override
				public void addTrim(int areaId, IWindowTrim trim, IWindowTrim beforeMe) {
				}

				@Override
				public void removeTrim(IWindowTrim toRemove) {
				}

				@Override
				public IWindowTrim getTrim(String id) {
					return null;
				}

				@Override
				public int[] getAreaIds() {
					return null;
				}

				@Override
				public List getAreaTrim(int areaId) {
					return null;
				}

				@Override
				public void updateAreaTrim(int id, List trim, boolean removeExtra) {
				}

				@Override
				public List getAllTrim() {
					return null;
				}

				@Override
				public void setTrimVisible(IWindowTrim trim, boolean visible) {
				}

				@Override
				public void forceLayout() {
				}
			};
		}
		return trimManager;
	}

	private final void initializeDefaultServices() {
		IEclipseContext windowContext = model.getContext();
		serviceLocator.registerService(IWorkbenchLocationService.class,
				new WorkbenchLocationService(IServiceScopes.WINDOW_SCOPE, getWorkbench(), this,
						null, null, null, 1));
		serviceLocator.registerService(IWorkbenchWindow.class, this);

		final ActionCommandMappingService mappingService = new ActionCommandMappingService();
		serviceLocator.registerService(IActionCommandMappingService.class, mappingService);

		selectionService = ContextInjectionFactory.make(SelectionService.class, model.getContext());
		serviceLocator.registerService(ISelectionService.class, selectionService);

		LegacyHandlerService hs = new LegacyHandlerService(windowContext);
		windowContext.set(IHandlerService.class.getName(), hs);

		final LegacyActionPersistence actionPersistence = new LegacyActionPersistence(this);
		serviceLocator.registerService(LegacyActionPersistence.class, actionPersistence);
		actionPersistence.read();

		ICommandService cmdService = workbench.getService(ICommandService.class);
		SlaveCommandService slaveCmdService = new SlaveCommandService(cmdService,
				IServiceScopes.WINDOW_SCOPE, this, model.getContext());
		serviceLocator.registerService(ICommandService.class, slaveCmdService);
		serviceLocator.registerService(IUpdateService.class, slaveCmdService);

		IContextService cxs = ContextInjectionFactory
				.make(ContextService.class, model.getContext());
		serviceLocator.registerService(IContextService.class, cxs);

		IMenuService parent = getWorkbench().getService(IMenuService.class);
		IMenuService msvs = new SlaveMenuService(parent, model);
		serviceLocator.registerService(IMenuService.class, msvs);
	}

	@Override
	public final Object getService(final Class key) {
		return serviceLocator.getService(key);
	}

	@Override
	public final boolean hasService(final Class key) {
		return serviceLocator.hasService(key);
	}

	public void toggleToolbarVisibility() {
		boolean coolbarVisible = getCoolBarVisible();
		boolean perspectivebarVisible = getPerspectiveBarVisible();

		if (getWindowConfigurer().getShowCoolBar()) {
			setCoolBarVisible(!coolbarVisible);
		}
		if (getWindowConfigurer().getShowPerspectiveBar()) {
			setPerspectiveBarVisible(!perspectivebarVisible);
		}
		ICommandService commandService = (ICommandService) getService(ICommandService.class);
		Map filter = new HashMap();
		filter.put(IServiceScopes.WINDOW_SCOPE, this);
		commandService.refreshElements(COMMAND_ID_TOGGLE_COOLBAR, filter);
	}

	public boolean isToolbarVisible() {
		return (getCoolBarVisible() && getWindowConfigurer().getShowCoolBar())
				|| (getPerspectiveBarVisible() && getWindowConfigurer().getShowPerspectiveBar());
	}

	private void updateLayoutDataForContents() {
		MTrimBar topTrim = getTopTrim();
		if (topTrim != null) {
			topTrim.setVisible(isToolbarVisible());
			getShell().layout();
		}
	}

		backgroundSaveListeners.add(listener);
	}

		Object[] listeners = backgroundSaveListeners.getListeners();
		for (int i = 0; i < listeners.length; i++) {
			IBackgroundSaveListener listener = (IBackgroundSaveListener) listeners[i];
			listener.handleBackgroundSaveStarted();
		}
	}

		backgroundSaveListeners.remove(listener);
	}

	public MWindow getModel() {
		return model;
	}

	StatusLineManager statusLineManager = null;

	public StatusLineManager getStatusLineManager() {
		if (statusLineManager == null) {
			statusLineManager = new StatusLineManager();
		}
		return statusLineManager;
	}

	private CoolBarManager2 oldCBM = new CoolBarManager2();
	private CoolBarToTrimManager coolbarToTrim;

	public ICoolBarManager getCoolBarManager2() {
		if (coolbarToTrim == null) {
			coolbarToTrim = new CoolBarToTrimManager(application, model, workbenchTrimElements,
					rendererFactory);
		}
		return coolbarToTrim;
	}

	public CoolBarManager getCoolBarManager() {
		new Exception("Bad call to getCoolBarManager()").printStackTrace(); //$NON-NLS-1$
		return oldCBM;
	}

	private IContributionManagerOverrides toolbarOverride = new IContributionManagerOverrides() {

		@Override
		public Integer getAccelerator(IContributionItem item) {
			return null;
		}

		@Override
		public String getAcceleratorText(IContributionItem item) {
			return null;
		}

		@Override
		public Boolean getEnabled(IContributionItem item) {
			return null;
		}

		@Override
		public String getText(IContributionItem item) {
			return null;
		}

		@Override
		public Boolean getVisible(IContributionItem item) {
			if (page == null)
				return null;

			MPerspective curPersp = page.getCurrentPerspective();
			if (curPersp == null)
				return null;

			String id = CustomizePerspectiveDialog.getIDFromIContributionItem(item);
			if (id == null)
				return null;

			String hiddenToolItems = page.getHiddenItems();
			if (hiddenToolItems.contains(ModeledPageLayout.HIDDEN_TOOLBAR_PREFIX + id + ",")) { //$NON-NLS-1$
				return Boolean.FALSE;
			}
			return null;
		}
	};

	MenuManager menuManager = new MenuManager("MenuBar", "org.eclipse.ui.main.menu"); //$NON-NLS-1$//$NON-NLS-2$

	public MenuManager getMenuManager() {
		return menuManager;
	}

	public IMenuManager getMenuBarManager() {
		return menuManager;
	}

	private IContributionManagerOverrides menuOverride = new IContributionManagerOverrides() {

		@Override
		public Integer getAccelerator(IContributionItem item) {
			return null;
		}

		@Override
		public String getAcceleratorText(IContributionItem item) {
			return null;
		}

		@Override
		public Boolean getEnabled(IContributionItem item) {
			return null;
		}

		@Override
		public String getText(IContributionItem item) {
			return null;
		}

		@Override
		public Boolean getVisible(IContributionItem item) {
			if (page == null)
				return null;

			MPerspective curPersp = page.getCurrentPerspective();
			if (curPersp == null)
				return null;

			String id = CustomizePerspectiveDialog.getIDFromIContributionItem(item);
			if (id == null)
				return null;

			String hiddenToolItems = page.getHiddenItems();
			if (hiddenToolItems.contains(ModeledPageLayout.HIDDEN_MENU_PREFIX + id + ",")) { //$NON-NLS-1$
				return Boolean.FALSE;
			}
			return null;
		}
	};

	ToolBarManager2 toolBarManager = new ToolBarManager2();

	private Runnable menuUpdater;

	@Inject
	@Optional
	private ToolBarManagerRenderer toolBarManagerRenderer;

	public IToolBarManager2 getToolBarManager2() {
		return toolBarManager;
	}

	public IToolBarManager getToolBarManager() {
		return getToolBarManager2();
	}

	public CustomizePerspectiveDialog createCustomizePerspectiveDialog(Perspective persp,
			IEclipseContext context) {
		return new CustomizePerspectiveDialog(getWindowConfigurer(), persp, context);
	}
}
