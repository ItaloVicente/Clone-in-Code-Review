
package org.eclipse.ui.internal;

import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.ULocale.Category;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.CommandManager;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.EventManager;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.commands.contexts.ContextManager;
import org.eclipse.core.commands.contexts.ContextManagerEvent;
import org.eclipse.core.commands.contexts.IContextManagerListener;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionDelta;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPlatformRunnable;
import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IRegistryChangeEvent;
import org.eclipse.core.runtime.IRegistryChangeListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.InjectionException;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.internal.workbench.renderers.swt.IUpdateService;
import org.eclipse.e4.ui.internal.workbench.swt.E4Application;
import org.eclipse.e4.ui.internal.workbench.swt.IEventLoopAdvisor;
import org.eclipse.e4.ui.internal.workbench.swt.PartRenderingEngine;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.commands.MBindingContext;
import org.eclipse.e4.ui.model.application.commands.MBindingTable;
import org.eclipse.e4.ui.model.application.commands.MCategory;
import org.eclipse.e4.ui.model.application.commands.MCommand;
import org.eclipse.e4.ui.model.application.commands.MCommandsFactory;
import org.eclipse.e4.ui.model.application.commands.impl.CommandsFactoryImpl;
import org.eclipse.e4.ui.model.application.descriptor.basic.MPartDescriptor;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimElement;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.basic.impl.BasicFactoryImpl;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.model.application.ui.menu.MTrimContribution;
import org.eclipse.e4.ui.services.EContextService;
import org.eclipse.e4.ui.workbench.IModelResourceHandler;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.ExternalActionManager;
import org.eclipse.jface.action.ExternalActionManager.CommandCallback;
import org.eclipse.jface.action.ExternalActionManager.IActiveChecker;
import org.eclipse.jface.action.ExternalActionManager.IExecuteApplicable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.bindings.BindingManager;
import org.eclipse.jface.bindings.BindingManagerEvent;
import org.eclipse.jface.bindings.IBindingManagerListener;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.ModalContext;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.BidiUtils;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.OpenStrategy;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.service.runnable.StartupMonitor;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.graphics.DeviceData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IDecoratorManager;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.ILocalWorkingSetManager;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.ISaveableFilter;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.ISaveablesLifecycleListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.ISourceProvider;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkingSetManager;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.Saveable;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.XMLMemento;
import org.eclipse.ui.activities.IWorkbenchActivitySupport;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.eclipse.ui.commands.ICommandImageService;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.commands.IWorkbenchCommandSupport;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.contexts.IWorkbenchContextSupport;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.internal.StartupThreading.StartupRunnable;
import org.eclipse.ui.internal.actions.CommandAction;
import org.eclipse.ui.internal.activities.ws.WorkbenchActivitySupport;
import org.eclipse.ui.internal.browser.WorkbenchBrowserSupport;
import org.eclipse.ui.internal.commands.CommandImageManager;
import org.eclipse.ui.internal.commands.CommandImageService;
import org.eclipse.ui.internal.commands.CommandService;
import org.eclipse.ui.internal.commands.WorkbenchCommandSupport;
import org.eclipse.ui.internal.contexts.ActiveContextSourceProvider;
import org.eclipse.ui.internal.contexts.ContextService;
import org.eclipse.ui.internal.contexts.WorkbenchContextSupport;
import org.eclipse.ui.internal.dialogs.PropertyPageContributorManager;
import org.eclipse.ui.internal.e4.compatibility.CompatibilityEditor;
import org.eclipse.ui.internal.e4.compatibility.CompatibilityPart;
import org.eclipse.ui.internal.e4.compatibility.E4Util;
import org.eclipse.ui.internal.handlers.LegacyHandlerService;
import org.eclipse.ui.internal.help.WorkbenchHelpSystem;
import org.eclipse.ui.internal.intro.IIntroRegistry;
import org.eclipse.ui.internal.intro.IntroDescriptor;
import org.eclipse.ui.internal.keys.BindingService;
import org.eclipse.ui.internal.menus.FocusControlSourceProvider;
import org.eclipse.ui.internal.menus.WorkbenchMenuService;
import org.eclipse.ui.internal.misc.Policy;
import org.eclipse.ui.internal.misc.StatusUtil;
import org.eclipse.ui.internal.misc.UIStats;
import org.eclipse.ui.internal.model.ContributionService;
import org.eclipse.ui.internal.progress.ProgressManager;
import org.eclipse.ui.internal.progress.ProgressManagerUtil;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.registry.UIExtensionTracker;
import org.eclipse.ui.internal.registry.ViewDescriptor;
import org.eclipse.ui.internal.services.EvaluationService;
import org.eclipse.ui.internal.services.IServiceLocatorCreator;
import org.eclipse.ui.internal.services.IWorkbenchLocationService;
import org.eclipse.ui.internal.services.MenuSourceProvider;
import org.eclipse.ui.internal.services.ServiceLocator;
import org.eclipse.ui.internal.services.ServiceLocatorCreator;
import org.eclipse.ui.internal.services.SourceProviderService;
import org.eclipse.ui.internal.services.WorkbenchLocationService;
import org.eclipse.ui.internal.splash.EclipseSplashHandler;
import org.eclipse.ui.internal.splash.SplashHandlerFactory;
import org.eclipse.ui.internal.testing.ContributionInfoMessages;
import org.eclipse.ui.internal.testing.WorkbenchTestable;
import org.eclipse.ui.internal.themes.ColorDefinition;
import org.eclipse.ui.internal.themes.FontDefinition;
import org.eclipse.ui.internal.themes.ThemeElementHelper;
import org.eclipse.ui.internal.themes.WorkbenchThemeManager;
import org.eclipse.ui.internal.tweaklets.GrabFocus;
import org.eclipse.ui.internal.tweaklets.Tweaklets;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.intro.IIntroManager;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.ui.menus.IMenuService;
import org.eclipse.ui.model.IContributionService;
import org.eclipse.ui.operations.IWorkbenchOperationSupport;
import org.eclipse.ui.progress.IProgressService;
import org.eclipse.ui.progress.WorkbenchJob;
import org.eclipse.ui.services.IDisposable;
import org.eclipse.ui.services.IEvaluationService;
import org.eclipse.ui.services.IServiceScopes;
import org.eclipse.ui.services.ISourceProviderService;
import org.eclipse.ui.splash.AbstractSplashHandler;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.swt.IFocusService;
import org.eclipse.ui.testing.ContributionInfo;
import org.eclipse.ui.themes.IThemeManager;
import org.eclipse.ui.views.IViewDescriptor;
import org.eclipse.ui.views.IViewRegistry;
import org.eclipse.ui.wizards.IWizardRegistry;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.SynchronousBundleListener;
import org.osgi.service.event.EventHandler;
import org.osgi.util.tracker.ServiceTracker;

public final class Workbench extends EventManager implements IWorkbench,
		org.eclipse.e4.ui.workbench.IWorkbench {

	public static String WORKBENCH_AUTO_SAVE_JOB = "Workbench Auto-Save Job"; //$NON-NLS-1$

	private static final String WORKBENCH_AUTO_SAVE_BACKGROUND_JOB = "Workbench Auto-Save Background Job"; //$NON-NLS-1$

	private static String MEMENTO_KEY = "memento"; //$NON-NLS-1$

	private static final String PROP_VM = "eclipse.vm"; //$NON-NLS-1$
	private static final String PROP_VMARGS = "eclipse.vmargs"; //$NON-NLS-1$
	private static final String PROP_COMMANDS = "eclipse.commands"; //$NON-NLS-1$
	private static final String PROP_EXIT_CODE = "eclipse.exitcode"; //$NON-NLS-1$
	private static final String CMD_DATA = "-data"; //$NON-NLS-1$
	private static final String CMD_VMARGS = "-vmargs"; //$NON-NLS-1$

	private final class StartupProgressBundleListener implements SynchronousBundleListener {

		private final IProgressMonitor progressMonitor;

		private final int maximumProgressCount;

		private final List<String> starting;

		StartupProgressBundleListener(IProgressMonitor progressMonitor, int maximumProgressCount) {
			super();
			this.progressMonitor = progressMonitor;
			this.maximumProgressCount = maximumProgressCount;
			this.starting = new ArrayList<String>();
		}

		@Override
		public void bundleChanged(BundleEvent event) {
			int eventType = event.getType();
			String bundleName;

			synchronized (this) {
				if (eventType == BundleEvent.STARTING) {
					starting.add(bundleName = event.getBundle().getSymbolicName());
				} else if (eventType == BundleEvent.STARTED) {
					progressCount++;
					if (progressCount <= maximumProgressCount) {
						progressMonitor.worked(1);
					}
					int index = starting.lastIndexOf(event.getBundle().getSymbolicName());
					if (index >= 0) {
						starting.remove(index);
					}
					if (index != starting.size()) {
						return; // not currently displayed
					}
					bundleName = index == 0 ? null : (String) starting.get(index - 1);
				} else {
					return; // uninteresting event
				}
			}

			if (bundleName != null) {
				String taskName = NLS.bind(WorkbenchMessages.Startup_Loading, bundleName);
				progressMonitor.subTask(taskName);
			}
		}
	}

	public static final String EARLY_STARTUP_FAMILY = "earlyStartup"; //$NON-NLS-1$

	static final String VERSION_STRING[] = { "0.046", "2.0" }; //$NON-NLS-1$ //$NON-NLS-2$

	static final String DEFAULT_WORKBENCH_STATE_FILENAME = "workbench.xml"; //$NON-NLS-1$

	private static Workbench instance;

	private static WorkbenchTestable testableObject;

	private static boolean createSplash = true;

	private static AbstractSplashHandler splash;

	private Display display;

	private boolean workbenchAutoSave = true;


	private EditorHistory editorHistory;

	private boolean runEventLoop = true;

	private boolean isStarting = true;

	private boolean isClosing = false;

	private boolean windowsClosed = false;

	private int returnCode = PlatformUI.RETURN_UNSTARTABLE;

	private WorkbenchAdvisor advisor;

	private WorkbenchConfigurer workbenchConfigurer;

	private ExtensionEventHandler extensionEventHandler;

	private int largeUpdates = 0;

	private final ServiceLocator serviceLocator;

	private int progressCount = -1;

	private ListenerList workbenchListeners = new ListenerList(ListenerList.IDENTITY);

	private ServiceRegistration workbenchService;

	private MApplication application;

	private IEclipseContext e4Context;

	private IEventBroker eventBroker;

	boolean initializationDone = false;

	private WorkbenchWindow windowBeingCreated = null;

	private Listener backForwardListener;

	private Job autoSaveJob;

	private String id;
	private ServiceRegistration<?> e4WorkbenchService;

	private Workbench(Display display, final WorkbenchAdvisor advisor, MApplication app,
			IEclipseContext appContext) {
		super();
		this.id = createId();
		StartupThreading.setWorkbench(this);
		if (instance != null && instance.isRunning()) {
			throw new IllegalStateException(WorkbenchMessages.Workbench_CreatingWorkbenchTwice);
		}
		Assert.isNotNull(display);
		Assert.isNotNull(advisor);
		this.advisor = advisor;
		this.display = display;
		application = app;
		e4Context = appContext;
		Workbench.instance = this;
		eventBroker = e4Context.get(IEventBroker.class);

		appContext.set(getClass().getName(), this);
		appContext.set(IWorkbench.class, this);
		appContext.set(IEventLoopAdvisor.class, new IEventLoopAdvisor() {
			@Override
			public void eventLoopIdle(Display display) {
				advisor.eventLoopIdle(display);
			}

			@Override
			public void eventLoopException(Throwable exception) {
				advisor.eventLoopException(exception);
			}
		});

		extensionEventHandler = new ExtensionEventHandler(this);
		Platform.getExtensionRegistry().addRegistryChangeListener(extensionEventHandler);
		IServiceLocatorCreator slc = new ServiceLocatorCreator();
		serviceLocator = (ServiceLocator) slc.createServiceLocator(null, null, new IDisposable() {
			@Override
			public void dispose() {
				final Display display = getDisplay();
				if (display != null && !display.isDisposed()) {
					MessageDialog.openInformation(null,
							WorkbenchMessages.Workbench_NeedsClose_Title,
							WorkbenchMessages.Workbench_NeedsClose_Message);
					close(PlatformUI.RETURN_RESTART, true);
				}
			}
		}, appContext);
		serviceLocator.registerService(IServiceLocatorCreator.class, slc);
		serviceLocator.registerService(IWorkbenchLocationService.class,
				new WorkbenchLocationService(IServiceScopes.WORKBENCH_SCOPE, this, null, null,
						null, null, 0));
	}

	public static final Workbench getInstance() {
		return instance;
	}

	public static final int createAndRunWorkbench(final Display display,
			final WorkbenchAdvisor advisor) {
		final int[] returnCode = new int[1];
		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
			@Override
			public void run() {
				final String nlExtensions = Platform.getNLExtensions();
				if (nlExtensions.length() > 0) {
					ULocale.setDefault(Category.FORMAT,
							new ULocale(ULocale.getDefault(Category.FORMAT).getBaseName()
									+ nlExtensions));
				}

				System.setProperty(org.eclipse.e4.ui.workbench.IWorkbench.XMI_URI_ARG,
						"org.eclipse.ui.workbench/LegacyIDE.e4xmi"); //$NON-NLS-1$
				Object obj = getApplication(Platform.getCommandLineArgs());

				IPreferenceStore store = WorkbenchPlugin.getDefault().getPreferenceStore();
				if (!store.isDefault(IPreferenceConstants.LAYOUT_DIRECTION)) {
					int orientation = store.getInt(IPreferenceConstants.LAYOUT_DIRECTION);
					Window.setDefaultOrientation(orientation);
				}

				if (obj instanceof E4Application) {
					E4Application e4app = (E4Application) obj;
					E4Workbench e4Workbench = e4app.createE4Workbench(getApplicationContext(), display);

					Workbench workbench = new Workbench(display, advisor, e4Workbench
							.getApplication(), e4Workbench.getContext());

					if (createSplash)
						workbench.createSplashWrapper();

					AbstractSplashHandler handler = getSplash();

					boolean showProgress = PrefUtil.getAPIPreferenceStore().getBoolean(
									IWorkbenchPreferenceConstants.SHOW_PROGRESS_ON_STARTUP);

					IProgressMonitor progressMonitor = null;
					if (handler != null && showProgress) {
						progressMonitor = handler.getBundleProgressMonitor();
						if (progressMonitor != null) {
							double cutoff = 0.95;
							int expectedProgressCount = Math.max(1, WorkbenchPlugin.getDefault()
									.getBundleCount() / 10);
							progressMonitor.beginTask("", expectedProgressCount); //$NON-NLS-1$
							SynchronousBundleListener bundleListener = workbench.new StartupProgressBundleListener(
									progressMonitor, (int) (expectedProgressCount * cutoff));
							WorkbenchPlugin.getDefault().addBundleListener(bundleListener);
						}
					}
					MApplication appModel = e4Workbench.getApplication();
					setSearchContribution(appModel, true);
					returnCode[0] = workbench.runUI();
					if (returnCode[0] == PlatformUI.RETURN_OK) {
						e4Workbench.createAndRunUI(e4Workbench.getApplication());
						WorkbenchMenuService wms = (WorkbenchMenuService) e4Workbench.getContext()
								.get(IMenuService.class);
						wms.dispose();
					}
					if (returnCode[0] != PlatformUI.RETURN_UNSTARTABLE) {
						setSearchContribution(appModel, false);
						e4app.saveModel();
					}
					e4Workbench.close();
					returnCode[0] = workbench.returnCode;
				}
			}
		});
		return returnCode[0];
	}

	private static void setSearchContribution(MApplication app, boolean enabled) {
		for (MTrimContribution contribution : app.getTrimContributions()) {
			if ("org.eclipse.ui.ide.application.trimcontribution.QuickAccess".contains(contribution //$NON-NLS-1$
					.getElementId())) {
				contribution.setToBeRendered(enabled);
			}
		}
	}

	private static ServiceTracker instanceAppContext;

	static IApplicationContext getApplicationContext() {
		if (instanceAppContext == null) {
			instanceAppContext = new ServiceTracker(
					WorkbenchPlugin.getDefault().getBundleContext(), IApplicationContext.class
							.getName(), null);
			instanceAppContext.open();
		}
		return (IApplicationContext) instanceAppContext.getService();
	}

	static Object getApplication(String[] args) {
		IExtension extension = Platform.getExtensionRegistry().getExtension(Platform.PI_RUNTIME,
				Platform.PT_APPLICATIONS, "org.eclipse.e4.ui.workbench.swt.E4Application"); //$NON-NLS-1$

		Assert.isNotNull(extension);

		try {
			IConfigurationElement[] elements = extension.getConfigurationElements();
			if (elements.length > 0) {
				IConfigurationElement[] runs = elements[0].getChildren("run"); //$NON-NLS-1$
				if (runs.length > 0) {
					Object runnable;
					runnable = runs[0].createExecutableExtension("class");//$NON-NLS-1$
					if (runnable instanceof IPlatformRunnable || runnable instanceof IApplication)
						return runnable;
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Display createDisplay() {
		String applicationName = WorkbenchPlugin.getDefault().getAppName();
		if (applicationName != null) {
			Display.setAppName(applicationName);
		}

		Display newDisplay = Display.getCurrent();
		if (newDisplay == null) {
			if (Policy.DEBUG_SWT_GRAPHICS || Policy.DEBUG_SWT_DEBUG) {
				DeviceData data = new DeviceData();
				if (Policy.DEBUG_SWT_GRAPHICS) {
					data.tracking = true;
				}
				if (Policy.DEBUG_SWT_DEBUG) {
					data.debug = true;
				}
				newDisplay = new Display(data);
			} else {
				newDisplay = new Display();
			}
		}

		newDisplay.setWarnings(false);

		Thread.currentThread().setPriority(Math.min(Thread.MAX_PRIORITY, Thread.NORM_PRIORITY + 1));

		initializeImages();

		return newDisplay;
	}

	private void createSplashWrapper() {
		final Display display = getDisplay();
		String splashLoc = System.getProperty("org.eclipse.equinox.launcher.splash.location"); //$NON-NLS-1$
		final Image background = loadImage(splashLoc);

		SafeRunnable run = new SafeRunnable() {

			@Override
			public void run() throws Exception {
				if (!WorkbenchPlugin.isSplashHandleSpecified()) {
					createSplash = false;
					return;
				}

				getSplash();
				if (splash == null) {
					createSplash = false;
					return;
				}

				Shell splashShell = splash.getSplash();
				if (splashShell == null) {
					splashShell = WorkbenchPlugin.getSplashShell(display);

					if (splashShell == null)
						return;
					if (background != null)
						splashShell.setBackgroundImage(background);
				}

				Dictionary properties = new Hashtable();
				properties.put(Constants.SERVICE_RANKING, new Integer(Integer.MAX_VALUE));
				BundleContext context = WorkbenchPlugin.getDefault().getBundleContext();
				final ServiceRegistration registration[] = new ServiceRegistration[1];
				StartupMonitor startupMonitor = new StartupMonitor() {

					@Override
					public void applicationRunning() {
						if (background != null)
							background.dispose();
						registration[0].unregister(); // unregister ourself
						if (splash != null)
							splash.dispose();
						WorkbenchPlugin.unsetSplashShell(display);

						for (IWorkbenchWindow window : getWorkbenchWindows()) {
							IWorkbenchPage page = window.getActivePage();
							if (page != null) {
								((WorkbenchPage) page).fireInitialPartVisibilityEvents();
							}
						}
					}

					@Override
					public void update() {
					}
				};
				registration[0] = context.registerService(StartupMonitor.class.getName(), startupMonitor, properties);

				splash.init(splashShell);
			}

			@Override
			public void handleException(Throwable e) {
				StatusManager.getManager().handle(
						StatusUtil.newStatus(WorkbenchPlugin.PI_WORKBENCH,
								"Could not instantiate splash", e)); //$NON-NLS-1$
				createSplash = false;
				splash = null;
				if (background != null)
					background.dispose();

			}
		};
		SafeRunner.run(run);
	}

	private Image loadImage(String splashLoc) {
		Image background = null;
		if (splashLoc != null) {
			InputStream input = null;
			try {
				input = new BufferedInputStream(new FileInputStream(splashLoc));
				background = new Image(display, input);
			} catch (SWTException e) {
				StatusManager.getManager().handle(
						StatusUtil.newStatus(WorkbenchPlugin.PI_WORKBENCH, e));
			} catch (IOException e) {
				StatusManager.getManager().handle(
						StatusUtil.newStatus(WorkbenchPlugin.PI_WORKBENCH, e));
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
					}
				}
			}
		}
		return background;
	}

	private static AbstractSplashHandler getSplash() {
		if (!createSplash)
			return null;

		if (splash == null) {

			IProduct product = Platform.getProduct();
			if (product != null)
				splash = SplashHandlerFactory.findSplashHandlerFor(product);

			if (splash == null)
				splash = new EclipseSplashHandler();
		}
		return splash;
	}

	public static WorkbenchTestable getWorkbenchTestable() {
		if (testableObject == null) {
			testableObject = new WorkbenchTestable();
		}
		return testableObject;
	}

	@Override
	public void addWorkbenchListener(IWorkbenchListener listener) {
		workbenchListeners.add(listener);
	}

	@Override
	public void removeWorkbenchListener(IWorkbenchListener listener) {
		workbenchListeners.remove(listener);
	}

	boolean firePreShutdown(final boolean forced) {
		Object list[] = workbenchListeners.getListeners();
		for (int i = 0; i < list.length; i++) {
			final IWorkbenchListener l = (IWorkbenchListener) list[i];
			final boolean[] result = new boolean[] { false };
			SafeRunnable.run(new SafeRunnable() {
				@Override
				public void run() {
					result[0] = l.preShutdown(Workbench.this, forced);
				}
			});
			if (!result[0]) {
				return false;
			}
		}
		return true;
	}

	void firePostShutdown() {
		Object list[] = workbenchListeners.getListeners();
		for (int i = 0; i < list.length; i++) {
			final IWorkbenchListener l = (IWorkbenchListener) list[i];
			SafeRunnable.run(new SafeRunnable() {
				@Override
				public void run() {
					l.postShutdown(Workbench.this);
				}
			});
		}
	}

	@Override
	public void addWindowListener(IWindowListener l) {
		addListenerObject(l);
	}

	@Override
	public void removeWindowListener(IWindowListener l) {
		removeListenerObject(l);
	}

	protected void fireWindowOpened(final IWorkbenchWindow window) {
		Object list[] = getListeners();
		for (int i = 0; i < list.length; i++) {
			final IWindowListener l = (IWindowListener) list[i];
			SafeRunner.run(new SafeRunnable() {
				@Override
				public void run() {
					l.windowOpened(window);
				}
			});
		}
	}

	protected void fireWindowClosed(final IWorkbenchWindow window) {
		Object list[] = getListeners();
		for (int i = 0; i < list.length; i++) {
			final IWindowListener l = (IWindowListener) list[i];
			SafeRunner.run(new SafeRunnable() {
				@Override
				public void run() {
					l.windowClosed(window);
				}
			});
		}
	}

	protected void fireWindowActivated(final IWorkbenchWindow window) {
		Object list[] = getListeners();
		for (int i = 0; i < list.length; i++) {
			final IWindowListener l = (IWindowListener) list[i];
			SafeRunner.run(new SafeRunnable() {
				@Override
				public void run() {
					l.windowActivated(window);
				}
			});
		}
	}

	protected void fireWindowDeactivated(final IWorkbenchWindow window) {
		Object list[] = getListeners();
		for (int i = 0; i < list.length; i++) {
			final IWindowListener l = (IWindowListener) list[i];
			SafeRunner.run(new SafeRunnable() {
				@Override
				public void run() {
					l.windowDeactivated(window);
				}
			});
		}
	}

	private boolean busyClose(final boolean force) {
		UIEvents.publishEvent(UIEvents.UILifeCycle.APP_SHUTDOWN_STARTED, application);

		isClosing = advisor.preShutdown();
		if (!force && !isClosing) {
			return false;
		}

		isClosing = firePreShutdown(force);
		if (!force && !isClosing) {
			return false;
		}

		isClosing = saveAllEditors(!force, true);
		if (!force && !isClosing) {
			return false;
		}
		
		if(autoSaveJob != null) {
			autoSaveJob.cancel();
			autoSaveJob = null;
		}

		boolean closeEditors = !force
				&& PrefUtil.getAPIPreferenceStore().getBoolean(
						IWorkbenchPreferenceConstants.CLOSE_EDITORS_ON_EXIT);
		if (closeEditors) {
			SafeRunner.run(new SafeRunnable() {
				@Override
				public void run() {
					IWorkbenchWindow windows[] = getWorkbenchWindows();
					for (int i = 0; i < windows.length; i++) {
						IWorkbenchPage pages[] = windows[i].getPages();
						for (int j = 0; j < pages.length; j++) {
							isClosing = isClosing && pages[j].closeAllEditors(false);
						}
					}
				}
			});
			if (!force && !isClosing) {
				return false;
			}
		}

		persist(true);

		if (!force && !isClosing) {
			return false;
		}

		SafeRunner.run(new SafeRunnable(WorkbenchMessages.ErrorClosing) {
			@Override
			public void run() {
				if (isClosing || force) {
					E4Util.unsupported("Need to close since no windowManager"); //$NON-NLS-1$
					MWindow selectedWindow = application.getSelectedElement();
					WorkbenchWindow selected = null;
					for (IWorkbenchWindow window : getWorkbenchWindows()) {
						WorkbenchWindow ww = (WorkbenchWindow) window;
						if (ww.getModel() == selectedWindow) {
							selected = ww;
						} else {
							((WorkbenchWindow) window).close(false);
						}
					}

					if (selected != null) {
						selected.close(false);
					}

					windowsClosed = true;
				}
			}
		});

		if (!force && !isClosing) {
			return false;
		}

		shutdown();

		IPresentationEngine engine = application.getContext().get(IPresentationEngine.class);
		engine.stop();

		runEventLoop = false;
		return true;
	}

	private void persist(final boolean shutdown) {
		SafeRunner.run(new SafeRunnable() {
			@Override
			public void run() {
				IWorkbenchWindow windows[] = getWorkbenchWindows();
				for (int i = 0; i < windows.length; i++) {
					IWorkbenchPage pages[] = windows[i].getPages();
					for (int j = 0; j < pages.length; j++) {
						List<EditorReference> editorReferences = ((WorkbenchPage) pages[j])
								.getInternalEditorReferences();
						List<EditorReference> referencesToClose = new ArrayList<EditorReference>();
						for (EditorReference reference : editorReferences) {
							IEditorPart editor = reference.getEditor(false);
							if (editor != null && !reference.persist() && shutdown) {
								referencesToClose.add(reference);
							}
						}
						if (shutdown) {
							for (EditorReference reference : referencesToClose) {
								((WorkbenchPage) pages[j]).closeEditor(reference);
							}
						}
					}
				}
			}
		});

		if (getWorkbenchConfigurer().getSaveAndRestore()) {
			SafeRunner.run(new SafeRunnable() {
				@Override
				public void run() {
					persistWorkbenchState();
				}

				@Override
				public void handleException(Throwable e) {
					String message;
					if (e.getMessage() == null) {
						message = WorkbenchMessages.ErrorClosingNoArg;
					} else {
						message = NLS.bind(WorkbenchMessages.ErrorClosingOneArg, e.getMessage());
					}

					if (!MessageDialog.openQuestion(null, WorkbenchMessages.Error, message)) {
						isClosing = false;
					}
				}
			});
		}

		SafeRunner.run(new SafeRunnable() {
			@Override
			public void run() {
				IWorkbenchWindow windows[] = getWorkbenchWindows();
				for (int i = 0; i < windows.length; i++) {
					IWorkbenchPage pages[] = windows[i].getPages();
					for (int j = 0; j < pages.length; j++) {
						IViewReference[] references = pages[j].getViewReferences();
						for (int k = 0; k < references.length; k++) {
							if (references[k].getView(false) != null) {
								((ViewReference) references[k]).persist();
							}
						}
					}
				}
			}
		});

		if (!shutdown) {
			persistWorkbenchModel();
		}
	}

	private boolean detectWorkbenchCorruption(MApplication application) {
		if (application.getChildren().isEmpty()) {
			WorkbenchPlugin.log(
					"When auto-saving the workbench model, there were no top-level windows. " //$NON-NLS-1$
							+ " Skipped saving the model.", //$NON-NLS-1$
					new Exception()); // log a stack trace to assist debugging
			return true;
		}
		return false;
	}

	private void persistWorkbenchModel() {
		if (Job.getJobManager().find(WORKBENCH_AUTO_SAVE_JOB).length > 0) {
			return;
		}
		final MApplication appCopy = (MApplication) EcoreUtil.copy((EObject) application);
		if (detectWorkbenchCorruption(appCopy)) {
			return;
		}
		final IModelResourceHandler handler = e4Context.get(IModelResourceHandler.class);

		Job cleanAndSaveJob = new Job(WORKBENCH_AUTO_SAVE_BACKGROUND_JOB) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				final Resource res = handler.createResourceWithApp(appCopy);
				cleanUpCopy(appCopy, e4Context);
				try {
					if (!detectWorkbenchCorruption((MApplication) res.getContents().get(0))) {
						res.save(null);
					}
				} catch (IOException e) {
				} finally {
					res.unload();
					res.getResourceSet().getResources().remove(res);
				}
				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				return WORKBENCH_AUTO_SAVE_JOB.equals(family);
			}

		};
		cleanAndSaveJob.setPriority(Job.SHORT);
		cleanAndSaveJob.setSystem(true);
		cleanAndSaveJob.schedule();
	}

	private static void cleanUpCopy(MApplication appCopy, IEclipseContext context) {
		setSearchContribution(appCopy, false);
		EModelService modelService = context.get(EModelService.class);
		List<MWindow> windows = modelService.findElements(appCopy, null, MWindow.class, null);
		for (MWindow window : windows) {
			if (window instanceof MTrimmedWindow) {
				MTrimmedWindow trimmedWindow = (MTrimmedWindow) window;
				window.setMainMenu(null);
				for (MTrimBar trimBar : trimmedWindow.getTrimBars()) {
					cleanUpTrimBar(trimBar);
				}
			}
		}
		appCopy.getMenuContributions().clear();
		appCopy.getToolBarContributions().clear();
		appCopy.getTrimContributions().clear();

		List<MPart> parts = modelService.findElements(appCopy, null, MPart.class, null);
		for (MPart part : parts) {
			for (MMenu menu : part.getMenus()) {
				menu.getChildren().clear();
			}
			MToolBar tb = part.getToolbar();
			if (tb != null) {
				tb.getChildren().clear();
			}
		}
	}

	private static void cleanUpTrimBar(MTrimBar element) {
		for (MTrimElement child : element.getPendingCleanup()) {
			element.getChildren().remove(child);
		}
		element.getPendingCleanup().clear();
	}

	@Override
	public boolean saveAllEditors(boolean confirm) {
		return saveAllEditors(confirm, false);
	}

	private boolean saveAllEditors(boolean confirm, boolean closing) {
		IWorkbenchWindow[] windows = getWorkbenchWindows();
		if (windows.length == 0) {
			return true;
		}

		Set<ISaveablePart> dirtyParts = new HashSet<ISaveablePart>();
		for (IWorkbenchWindow window : windows) {
			WorkbenchPage page = (WorkbenchPage) window.getActivePage();
			if (page != null) {
				Collections.addAll(dirtyParts, page.getDirtyParts());
			}
		}

		IWorkbenchWindow activeWindow = getActiveWorkbenchWindow();
		if (activeWindow == null) {
			activeWindow = windows[0];
		}
		return WorkbenchPage.saveAll(new ArrayList<ISaveablePart>(dirtyParts),
				confirm, closing, true, activeWindow, activeWindow);
	}

	@Override
	public boolean close() {
		return close(PlatformUI.RETURN_OK, false);
	}

	boolean close(int returnCode, final boolean force) {
		this.returnCode = returnCode;
		final boolean[] ret = new boolean[1];
		BusyIndicator.showWhile(null, new Runnable() {
			@Override
			public void run() {
				ret[0] = busyClose(force);
			}
		});
		return ret[0];
	}

	@Override
	public IWorkbenchWindow getActiveWorkbenchWindow() {
		if (Display.getCurrent() == null || !initializationDone) {
			return null;
		}

		if (windowsClosed) {
			return null;
		}

		if (e4Context.get(IPresentationEngine.class) == null) {
			return null;
		}

		MWindow activeWindow = application.getSelectedElement();
		if (activeWindow == null && !application.getChildren().isEmpty()) {
			activeWindow = application.getChildren().get(0);
		}

		if (activeWindow != null && activeWindow.getWidget() == null) {
			return null;
		}

		IWorkbenchWindow iWorkbenchWindow = activeWindow.getContext().get(IWorkbenchWindow.class);
		if (iWorkbenchWindow != null) {
			return iWorkbenchWindow;
		}
		return createWorkbenchWindow(getDefaultPageInput(), getPerspectiveRegistry()
				.findPerspectiveWithId(getPerspectiveRegistry().getDefaultPerspective()),
				activeWindow, false);
	}

	IWorkbenchWindow createWorkbenchWindow(IAdaptable input, IPerspectiveDescriptor descriptor,
			MWindow window, boolean newWindow) {

		IEclipseContext windowContext = window.getContext();
		if (windowContext == null) {
			windowContext = E4Workbench.initializeContext(e4Context, window);
		}
		WorkbenchWindow result = (WorkbenchWindow) windowContext.get(IWorkbenchWindow.class);
		if (result == null) {
			if (windowBeingCreated != null)
				return windowBeingCreated;
			result = new WorkbenchWindow(input, descriptor);
			windowBeingCreated = result;
			try {
				if (newWindow) {
					Point size = result.getWindowConfigurer().getInitialSize();
					window.setWidth(size.x);
					window.setHeight(size.y);
					application.getChildren().add(window);
					application.setSelectedElement(window);
				}
				ContextInjectionFactory.inject(result, windowContext);
				windowContext.set(IWorkbenchWindow.class, result);
			} finally {
				windowBeingCreated = null;
			}

			if (application.getSelectedElement() == window) {
				application.getContext().set(ISources.ACTIVE_WORKBENCH_WINDOW_NAME, result);
				application.getContext().set(ISources.ACTIVE_WORKBENCH_WINDOW_SHELL_NAME, result.getShell());
			}

			fireWindowOpened(result);
			result.fireWindowOpened();
		}
		return result;
	}

	public EditorHistory getEditorHistory() {
		if (editorHistory == null) {
			editorHistory = new EditorHistory();
		}
		return editorHistory;
	}

	@Override
	public IEditorRegistry getEditorRegistry() {
		return WorkbenchPlugin.getDefault().getEditorRegistry();
	}

	@Override
	public IWorkbenchOperationSupport getOperationSupport() {
		return WorkbenchPlugin.getDefault().getOperationSupport();
	}

	@Override
	public IPerspectiveRegistry getPerspectiveRegistry() {
		return WorkbenchPlugin.getDefault().getPerspectiveRegistry();
	}

	@Override
	public PreferenceManager getPreferenceManager() {
		return WorkbenchPlugin.getDefault().getPreferenceManager();
	}

	@Override
	public IPreferenceStore getPreferenceStore() {
		return WorkbenchPlugin.getDefault().getPreferenceStore();
	}

	@Override
	public ISharedImages getSharedImages() {
		return WorkbenchPlugin.getDefault().getSharedImages();
	}



	@Override
	public int getWorkbenchWindowCount() {
		return getWorkbenchWindows().length;
	}

	@Override
	public IWorkbenchWindow[] getWorkbenchWindows() {
		List<IWorkbenchWindow> windows = new ArrayList<IWorkbenchWindow>();
		for (MWindow window : application.getChildren()) {
			IEclipseContext context = window.getContext();
			if (context != null) {
				IWorkbenchWindow wwindow = (IWorkbenchWindow) context.get(IWorkbenchWindow.class
						.getName());
				if (wwindow != null) {
					windows.add(wwindow);
				}
			}
		}
		return windows.toArray(new IWorkbenchWindow[windows.size()]);
	}

	@Override
	public IWorkingSetManager getWorkingSetManager() {
		return WorkbenchPlugin.getDefault().getWorkingSetManager();
	}

	@Override
	public ILocalWorkingSetManager createLocalWorkingSetManager() {
		return new LocalWorkingSetManager(WorkbenchPlugin.getDefault().getBundleContext());
	}

	private boolean init() {
		if (WorkbenchPlugin.getDefault().isDebugging()) {
			WorkbenchPlugin.DEBUG = true;
			ModalContext.setDebugMode(true);
		}

		JFaceUtil.initializeJFacePreferences();


		e4Context.set("org.eclipse.core.runtime.Platform", Platform.class); //$NON-NLS-1$
		final EvaluationService evaluationService = new EvaluationService(e4Context);

		StartupThreading.runWithoutExceptions(new StartupRunnable() {

			@Override
			public void runWithException() {
				serviceLocator.registerService(IEvaluationService.class, evaluationService);
			}
		});

		initializeLazyServices();


		activityHelper = ActivityPersistanceHelper.getInstance();
		StartupThreading.runWithoutExceptions(new StartupRunnable() {

			@Override
			public void runWithException() {
				WorkbenchImages.getImageRegistry();
			}
		});
		initializeE4Services();
		IIntroRegistry introRegistry = WorkbenchPlugin.getDefault().getIntroRegistry();
		if (introRegistry.getIntroCount() > 0) {
			IProduct product = Platform.getProduct();
			if (product != null) {
				introDescriptor = (IntroDescriptor) introRegistry.getIntroForProduct(product
						.getId());
			}
		}
		initializeDefaultServices();
		initializeFonts();
		initializeColors();
		initializeApplicationColors();

		StartupThreading.runWithoutExceptions(new StartupRunnable() {

			@Override
			public void runWithException() {
				advisor.internalBasicInitialize(getWorkbenchConfigurer());
			}
		});

		boolean useColorIcons = PrefUtil.getInternalPreferenceStore().getBoolean(
				IPreferenceConstants.COLOR_ICONS);
		ActionContributionItem.setUseColorIconsInToolbars(useColorIcons);

		initializeSingleClickOption();

		initializeGlobalization();
		initializeNLExtensions();

		initializeWorkbenchImages();

		StartupThreading.runWithoutExceptions(new StartupRunnable() {

			@Override
			public void runWithException() {
				((GrabFocus) Tweaklets.get(GrabFocus.KEY)).init(getDisplay());
			}
		});

		try {
			UIStats.start(UIStats.RESTORE_WORKBENCH, "Workbench"); //$NON-NLS-1$

			final boolean bail[] = new boolean[1];
			StartupThreading.runWithoutExceptions(new StartupRunnable() {

				@Override
				public void runWithException() throws Throwable {
					advisor.preStartup();
					initializationDone = true;
					if (isClosing() || !advisor.openWindows()) {
						bail[0] = true;
					}

					restoreWorkbenchState();
				}
			});

			if (bail[0])
				return false;

		} finally {
			UIStats.end(UIStats.RESTORE_WORKBENCH, this, "Workbench"); //$NON-NLS-1$
		}


		return true;
	}

	private void initializeWorkbenchImages() {
		StartupThreading.runWithoutExceptions(new StartupRunnable() {
			@Override
			public void runWithException() {
				WorkbenchImages.getDescriptors();
			}
		});
	}

	private void initializeCommandResolver() {
		ExternalActionManager.getInstance().setCallback(
				new CommandCallback(bindingManager, commandManager, new IActiveChecker() {
					@Override
					public final boolean isActive(final String commandId) {
						return workbenchActivitySupport.getActivityManager().getIdentifier(
								commandId).isEnabled();
					}
				}, new IExecuteApplicable() {
					@Override
					public boolean isApplicable(IAction action) {
						return !(action instanceof CommandAction);
					}
				}));
	}

	private void initializeApplicationColors() {
		StartupThreading.runWithoutExceptions(new StartupRunnable() {

			@Override
			public void runWithException() {
				ColorDefinition[] colorDefinitions = WorkbenchPlugin.getDefault()
						.getThemeRegistry().getColors();
				ThemeElementHelper.populateRegistry(getThemeManager().getCurrentTheme(),
						colorDefinitions, PrefUtil.getInternalPreferenceStore());
			}
		});
	}

	void initializeSingleClickOption() {
		IPreferenceStore store = WorkbenchPlugin.getDefault().getPreferenceStore();
		boolean openOnSingleClick = store.getBoolean(IPreferenceConstants.OPEN_ON_SINGLE_CLICK);
		boolean selectOnHover = store.getBoolean(IPreferenceConstants.SELECT_ON_HOVER);
		boolean openAfterDelay = store.getBoolean(IPreferenceConstants.OPEN_AFTER_DELAY);
		int singleClickMethod = openOnSingleClick ? OpenStrategy.SINGLE_CLICK
				: OpenStrategy.DOUBLE_CLICK;
		if (openOnSingleClick) {
			if (selectOnHover) {
				singleClickMethod |= OpenStrategy.SELECT_ON_HOVER;
			}
			if (openAfterDelay) {
				singleClickMethod |= OpenStrategy.ARROW_KEYS_OPEN;
			}
		}
		OpenStrategy.setOpenMethod(singleClickMethod);
	}

	private void initializeGlobalization() {
		IPreferenceStore store = WorkbenchPlugin.getDefault().getPreferenceStore();

		if (!store.isDefault(IPreferenceConstants.BIDI_SUPPORT)) {
			BidiUtils.setBidiSupport(store.getBoolean(IPreferenceConstants.BIDI_SUPPORT));
		}
		if (!store.isDefault(IPreferenceConstants.TEXT_DIRECTION)) {
			BidiUtils.setTextDirection(store.getString(IPreferenceConstants.TEXT_DIRECTION));
		}
	}

	private void initializeNLExtensions() {
		IPreferenceStore store = WorkbenchPlugin.getDefault().getPreferenceStore();
		if (!store.isDefault(IPreferenceConstants.NL_EXTENSIONS)) {
			String nlExtensions = store.getString(IPreferenceConstants.NL_EXTENSIONS);
			ULocale.setDefault(Category.FORMAT, new ULocale(ULocale.getDefault(Category.FORMAT)
					.getBaseName() + nlExtensions));
		}
	}

	private void initializeFonts() {
		StartupThreading.runWithoutExceptions(new StartupRunnable() {

			@Override
			public void runWithException() {
				FontDefinition[] fontDefinitions = WorkbenchPlugin.getDefault().getThemeRegistry()
						.getFonts();

				ThemeElementHelper.populateRegistry(getThemeManager().getCurrentTheme(),
						fontDefinitions, PrefUtil.getInternalPreferenceStore());
			}
		});
	}

	private static void initializeImages() {
		ImageDescriptor[] windowImages = WorkbenchPlugin.getDefault().getWindowImages();
		if (windowImages == null) {
			return;
		}

		Image[] images = new Image[windowImages.length];
		for (int i = 0; i < windowImages.length; ++i) {
			images[i] = windowImages[i].createImage();
		}
		Window.setDefaultImages(images);
	}

	private void uninitializeImages() {
		WorkbenchImages.dispose();
		Image[] images = Window.getDefaultImages();
		Window.setDefaultImage(null);
		for (int i = 0; i < images.length; i++) {
			images[i].dispose();
		}
	}

	private void initializeColors() {
		StartupThreading.runWithoutExceptions(new StartupRunnable() {
			@Override
			public void runWithException() {
				WorkbenchColors.startup();
			}
		});
	}

	@Override
	public boolean isClosing() {
		return isClosing;
	}

	private final void initializeE4Services() {
		IPreferenceStore preferenceStore = PrefUtil.getAPIPreferenceStore();
		preferenceStore.addPropertyChangeListener(new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (IWorkbenchPreferenceConstants.ENABLE_ANIMATIONS.equals(event.getProperty())) {
					Object o = event.getNewValue();
					if (o instanceof Boolean) {
						e4Context.set(IPresentationEngine.ANIMATIONS_ENABLED, o);
					} else if (o instanceof String) {
						e4Context.set(IPresentationEngine.ANIMATIONS_ENABLED,
								Boolean.parseBoolean((String) event.getNewValue()));
					}
				}
			}
		});

		eventBroker.subscribe(UIEvents.ElementContainer.TOPIC_CHILDREN, new EventHandler() {
			@Override
			public void handleEvent(org.osgi.service.event.Event event) {
				if (application == event.getProperty(UIEvents.EventTags.ELEMENT)) {
					if (UIEvents.isREMOVE(event)) {
						for (Object removed : UIEvents.asIterable(event,
								UIEvents.EventTags.OLD_VALUE)) {
							MWindow window = (MWindow) removed;
							IEclipseContext windowContext = window.getContext();
							if (windowContext != null) {
								IWorkbenchWindow wwindow = windowContext.get(IWorkbenchWindow.class);
								if (wwindow != null) {
									fireWindowClosed(wwindow);
								}
							}
						}
					}
				}
			}
		});
		eventBroker.subscribe(UIEvents.ElementContainer.TOPIC_SELECTEDELEMENT, new EventHandler() {
			@Override
			public void handleEvent(org.osgi.service.event.Event event) {
				if (application == event.getProperty(UIEvents.EventTags.ELEMENT)) {
					if (UIEvents.EventTypes.SET.equals(event
							.getProperty(UIEvents.EventTags.TYPE))) {
						MWindow window = (MWindow) event.getProperty(UIEvents.EventTags.NEW_VALUE);
						if (window != null) {
							IWorkbenchWindow wwindow = window.getContext().get(IWorkbenchWindow.class);
							if (wwindow != null) {
								e4Context.set(ISources.ACTIVE_WORKBENCH_WINDOW_NAME, wwindow);
								e4Context.set(ISources.ACTIVE_WORKBENCH_WINDOW_SHELL_NAME,
										wwindow.getShell());
							}
						}
					}
				}
			}
		});

		eventBroker.subscribe(
UIEvents.UIElement.TOPIC_TOBERENDERED, new EventHandler() {
			@Override
			public void handleEvent(org.osgi.service.event.Event event) {
				if (Boolean.TRUE.equals(event.getProperty(UIEvents.EventTags.NEW_VALUE))) {
					Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
					if (element instanceof MPart) {
						MPart part = (MPart) element;
						createReference(part);
					}
				}
			}
		});

		eventBroker.subscribe(
UIEvents.Context.TOPIC_CONTEXT,
				new EventHandler() {
					@Override
					public void handleEvent(org.osgi.service.event.Event event) {
						Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
						if (element instanceof MPart) {
							MPart part = (MPart) element;
							IEclipseContext context = part.getContext();
							if (context != null) {
								setReference(part, context);
							}
						}
					}
				});
		
		eventBroker.subscribe(UIEvents.ElementContainer.TOPIC_CHILDREN, new EventHandler() {
			@Override
			public void handleEvent(org.osgi.service.event.Event event) {
				Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
				if (!(element instanceof MApplication)) {
					return;
				}
				MApplication app = (MApplication) element;
				if (UIEvents.isREMOVE(event)) {
					if (app.getChildren().isEmpty()) {
						Object oldValue = event.getProperty(UIEvents.EventTags.OLD_VALUE);
						WorkbenchPlugin.log("The final top level window " + oldValue //$NON-NLS-1$
								+ " was just removed", new Exception()); //$NON-NLS-1$
					}
				}
			}
		});

		boolean found = false;
		List<MPartDescriptor> currentDescriptors = application.getDescriptors();
		for (MPartDescriptor desc : currentDescriptors) {
			if (desc.getElementId().equals(CompatibilityEditor.MODEL_ELEMENT_ID)) {
				found = true;
				break;
			}
		}
		if (!found) {
			MPartDescriptor descriptor = org.eclipse.e4.ui.model.application.descriptor.basic.impl.BasicFactoryImpl.eINSTANCE
					.createPartDescriptor();
			descriptor.getTags().add("Editor"); //$NON-NLS-1$
			descriptor.setCloseable(true);
			descriptor.setAllowMultiple(true);
			descriptor.setElementId(CompatibilityEditor.MODEL_ELEMENT_ID);
			descriptor.setContributionURI(CompatibilityPart.COMPATIBILITY_EDITOR_URI);
			descriptor.setCategory("org.eclipse.e4.primaryDataStack"); //$NON-NLS-1$
			application.getDescriptors().add(descriptor);
		}

		WorkbenchPlugin.getDefault().getViewRegistry();
	}

	private WorkbenchPage getWorkbenchPage(MPart part) {
		IEclipseContext context = getWindowContext(part);
		WorkbenchPage page = (WorkbenchPage) context.get(IWorkbenchPage.class);
		if (page == null) {
			MWindow window = context.get(MWindow.class);
			Workbench workbench = (Workbench) PlatformUI.getWorkbench();
			workbench.openWorkbenchWindow(getDefaultPageInput(), getPerspectiveRegistry()
					.findPerspectiveWithId(getDefaultPerspectiveId()),
					window, false);
			page = (WorkbenchPage) context.get(IWorkbenchPage.class);
		}
		return page;
	}

	private void setReference(MPart part, IEclipseContext context) {
		String uri = part.getContributionURI();
		if (CompatibilityPart.COMPATIBILITY_EDITOR_URI.equals(uri)) {
			WorkbenchPage page = getWorkbenchPage(part);
			EditorReference ref = page.getEditorReference(part);
			if (ref == null) {
				MPart clonedFrom = (MPart) part.getTransientData().get(EModelService.CLONED_FROM_KEY);
				if (clonedFrom != null && clonedFrom.getContext() != null) {
					EditorReference originalRef = page.getEditorReference(clonedFrom);
					if (originalRef != null) {
						IEditorInput partInput = null;
						String editorId = originalRef.getDescriptor().getId();
						try {
							partInput = originalRef.getEditorInput();
						} catch (PartInitException e) {
							System.out.println("Ooops !!!"); //$NON-NLS-1$
						}
						ref = page.createEditorReferenceForPart(part, partInput, editorId, null);
					}
				}

				if (ref == null) {
					ref = createEditorReference(part, page);
				}
			}
			context.set(EditorReference.class, ref);
		} else {
			WorkbenchPage page = getWorkbenchPage(part);
			ViewReference ref = page.getViewReference(part);
			if (ref == null) {
				ref = createViewReference(part, page);
			}
			context.set(ViewReference.class, ref);
		}
	}

	private ViewReference createViewReference(MPart part, WorkbenchPage page) {
		WorkbenchWindow window = (WorkbenchWindow) page.getWorkbenchWindow();

		String partId = part.getElementId();

		int colonIndex = partId.indexOf(':');
		String descId = colonIndex == -1 ? partId : partId.substring(0, colonIndex);

		IViewDescriptor desc = window.getWorkbench().getViewRegistry().find(descId);
		ViewReference ref = new ViewReference(window.getModel().getContext(), page, part,
				(ViewDescriptor) desc);
		page.addViewReference(ref);
		return ref;
	}

	private EditorReference createEditorReference(MPart part, WorkbenchPage page) {
		WorkbenchWindow window = (WorkbenchWindow) page.getWorkbenchWindow();
		EditorReference ref = new EditorReference(window.getModel().getContext(), page, part, null,
				null, null);
		page.addEditorReference(ref);
		return ref;
	}

	private void createReference(MPart part) {
		String uri = part.getContributionURI();
		if (CompatibilityPart.COMPATIBILITY_VIEW_URI.equals(uri)) {
			WorkbenchPage page = getWorkbenchPage(part);
			ViewReference ref = page.getViewReference(part);
			if (ref == null) {
				createViewReference(part, page);
			}
		} else if (CompatibilityPart.COMPATIBILITY_EDITOR_URI.equals(uri)) {
			WorkbenchPage page = getWorkbenchPage(part);
			EditorReference ref = page.getEditorReference(part);
			if (ref == null) {
				createEditorReference(part, page);
			}
		}
	}

	private IEclipseContext getWindowContext(MPart part) {
		MElementContainer<?> parent = (MElementContainer<?>) ((EObject) part).eContainer();
		while (!(parent instanceof MWindow)) {
			parent = (MElementContainer<?>) ((EObject) parent).eContainer(); // parent.getParent();
		}

		return ((MWindow) parent).getContext();
	}

	private final void initializeLazyServices() {
		e4Context.set(IExtensionTracker.class.getName(), new ContextFunction() {

			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				if (tracker == null) {
					tracker = new UIExtensionTracker(getDisplay());
				}
				return tracker;
			}
		});
		e4Context.set(IWorkbenchActivitySupport.class.getName(), new ContextFunction() {

			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				if (workbenchActivitySupport == null) {
					workbenchActivitySupport = new WorkbenchActivitySupport();
				}
				return workbenchActivitySupport;
			}
		});
		e4Context.set(IProgressService.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				return ProgressManager.getInstance();
			}
		});
		WorkbenchPlugin.getDefault().initializeContext(e4Context);
	}

	private ArrayList<MCommand> commandsToRemove = new ArrayList<MCommand>();
	private ArrayList<MCategory> categoriesToRemove = new ArrayList<MCategory>();

	private CommandService initializeCommandService(IEclipseContext appContext) {
		CommandService service = new CommandService(commandManager, appContext);
		appContext.set(ICommandService.class, service);
		appContext.set(IUpdateService.class, service);
		service.readRegistry();

		return service;
	}

	private Map<String, MBindingContext> bindingContexts = new HashMap<String, MBindingContext>();

	public MBindingContext getBindingContext(String id) {
		MBindingContext result = bindingContexts.get(id);
		if (result == null) {
			result = searchContexts(id, application.getRootContext());
			if (result == null) {
				result = MCommandsFactory.INSTANCE.createBindingContext();
				result.setElementId(id);
				result.setName("Auto::" + id); //$NON-NLS-1$
				application.getRootContext().add(result);
			}
			if (result != null) {
				bindingContexts.put(id, result);
			}
		}
		return result;
	}

	private MBindingContext searchContexts(String id, List<MBindingContext> rootContext) {
		for (MBindingContext context : rootContext) {
			if (context.getElementId().equals(id)) {
				return context;
			}
			MBindingContext result = searchContexts(id, context.getChildren());
			if (result != null) {
				return result;
			}
		}
		return null;
	}
	private void defineBindingTable(String id) {
		List<MBindingTable> bindingTables = application.getBindingTables();
		if (contains(bindingTables, id)) {
			return;
		}
		if (WorkbenchPlugin.getDefault().isDebugging()) {
			WorkbenchPlugin.log("Defining a binding table: " + id); //$NON-NLS-1$
		}
		MBindingTable bt = CommandsFactoryImpl.eINSTANCE.createBindingTable();
		bt.setBindingContext(getBindingContext(id));
		bindingTables.add(bt);
	}

	private boolean contains(List<MBindingTable> bindingTables, String id) {
		for (MBindingTable bt : bindingTables) {
			if (id.equals(bt.getBindingContext().getElementId())) {
				return true;
			}
		}
		return false;
	}

	private final void initializeDefaultServices() {

		final IContributionService contributionService = new ContributionService(getAdvisor());
		serviceLocator.registerService(IContributionService.class, contributionService);

		final IEvaluationService evaluationService = (IEvaluationService) serviceLocator
				.getService(IEvaluationService.class);

		StartupThreading.runWithoutExceptions(new StartupRunnable() {

			@Override
			public void runWithException() {
				serviceLocator.registerService(ISaveablesLifecycleListener.class,
						new SaveablesList());
			}
		});

		StartupThreading.runWithoutExceptions(new StartupRunnable() {

			@Override
			public void runWithException() {
				Command.DEBUG_COMMAND_EXECUTION = Policy.DEBUG_COMMANDS;
				commandManager = e4Context.get(CommandManager.class);
			}
		});

		final CommandService[] commandService = new CommandService[1];
		StartupThreading.runWithoutExceptions(new StartupRunnable() {

			@Override
			public void runWithException() {
				commandService[0] = initializeCommandService(e4Context);

			}
		});

		StartupThreading.runWithoutExceptions(new StartupRunnable() {

			@Override
			public void runWithException() {
				ContextManager.DEBUG = Policy.DEBUG_CONTEXTS;
				contextManager = e4Context.get(ContextManager.class);
			}
		});

		IContextService cxs = (IContextService) ContextInjectionFactory.make(ContextService.class,
				e4Context);

		final IContextService contextService = cxs;

		StartupThreading.runWithoutExceptions(new StartupRunnable() {

			@Override
			public void runWithException() {
				contextManager.addContextManagerListener(new IContextManagerListener() {
					@Override
					public void contextManagerChanged(ContextManagerEvent contextManagerEvent) {
						if (contextManagerEvent.isContextChanged()) {
							String id = contextManagerEvent.getContextId();
							if (id != null) {
								defineBindingTable(id);
							}
						}
					}
				});
				EContextService ecs = e4Context.get(EContextService.class);
				ecs.activateContext(IContextService.CONTEXT_ID_DIALOG_AND_WINDOW);
			}
		});

		serviceLocator.registerService(IContextService.class, contextService);

		final IBindingService[] bindingService = new BindingService[1];

		StartupThreading.runWithoutExceptions(new StartupRunnable() {

			@Override
			public void runWithException() {
				BindingManager.DEBUG = Policy.DEBUG_KEY_BINDINGS;
				bindingManager = e4Context.get(BindingManager.class);
				bindingService[0] = ContextInjectionFactory.make(
						BindingService.class, e4Context);
			}
		});

		serviceLocator.registerService(IBindingService.class, bindingService[0]);

		final CommandImageManager commandImageManager = new CommandImageManager();
		final CommandImageService commandImageService = new CommandImageService(
				commandImageManager, commandService[0]);
		commandImageService.readRegistry();
		serviceLocator.registerService(ICommandImageService.class, commandImageService);

		final WorkbenchMenuService menuService = new WorkbenchMenuService(serviceLocator, e4Context);

		serviceLocator.registerService(IMenuService.class, menuService);
		StartupThreading.runWithoutExceptions(new StartupRunnable() {

			@Override
			public void runWithException() {
				menuService.readRegistry();
			}
		});

		final SourceProviderService sourceProviderService = new SourceProviderService(
				serviceLocator);
		serviceLocator.registerService(ISourceProviderService.class, sourceProviderService);
		StartupThreading.runWithoutExceptions(new StartupRunnable() {

			@Override
			public void runWithException() {
				sourceProviderService.readRegistry();
				ISourceProvider[] sp = sourceProviderService.getSourceProviders();
				for (int i = 0; i < sp.length; i++) {
					evaluationService.addSourceProvider(sp[i]);
					if (!(sp[i] instanceof ActiveContextSourceProvider)) {
						contextService.addSourceProvider(sp[i]);
					}
				}
			}
		});

		StartupThreading.runWithoutExceptions(new StartupRunnable() {

			@Override
			public void runWithException() {

				FocusControlSourceProvider focusControl = (FocusControlSourceProvider) sourceProviderService
						.getSourceProvider(ISources.ACTIVE_FOCUS_CONTROL_ID_NAME);
				serviceLocator.registerService(IFocusService.class, focusControl);

				menuSourceProvider = (MenuSourceProvider) sourceProviderService
						.getSourceProvider(ISources.ACTIVE_MENU_NAME);
			}
		});

		final IHandlerService[] handlerService = new IHandlerService[1];
		StartupThreading.runWithoutExceptions(new StartupRunnable() {

			@Override
			public void runWithException() {
				handlerService[0] = new LegacyHandlerService(e4Context);
				e4Context.set(IHandlerService.class, handlerService[0]);
				handlerService[0].readRegistry();
			}
		});
		workbenchContextSupport = new WorkbenchContextSupport(this, contextManager);
		workbenchCommandSupport = new WorkbenchCommandSupport(bindingManager, commandManager,
				contextManager, handlerService[0]);
		initializeCommandResolver();

		bindingManager.addBindingManagerListener(bindingManagerListener);

		serviceLocator.registerService(ISelectionConversionService.class,
				new SelectionConversionService());

		backForwardListener = createBackForwardListener();
		StartupThreading.runWithoutExceptions(new StartupRunnable() {
			@Override
			public void runWithException() {
				getDisplay().addFilter(SWT.MouseDown, backForwardListener);
			}
		});
	}

	private Listener createBackForwardListener() {
		return new Listener() {
			@Override
			public void handleEvent(Event event) {
				String commandId;
				switch (event.button) {
				case 4:
				case 8:
					commandId = IWorkbenchCommandConstants.NAVIGATE_BACKWARD_HISTORY;
					break;
				case 5:
				case 9:
					commandId = IWorkbenchCommandConstants.NAVIGATE_FORWARD_HISTORY;
					break;
				default:
					return;
				}

				final IHandlerService handlerService = (IHandlerService) getService(IHandlerService.class);

				try {
					handlerService.executeCommand(commandId, event);
					event.doit = false;
				} catch (NotDefinedException e) {
				} catch (NotEnabledException e) {
				} catch (NotHandledException e) {
				} catch (ExecutionException ex) {
					StatusUtil.handleStatus(ex, StatusManager.SHOW | StatusManager.LOG);
				}
			}
		};
	}

	@Override
	public boolean isStarting() {
		return isStarting && isRunning();
	}

	void forceOpenPerspective() {
		if (getWorkbenchWindowCount() == 0) {
			return;
		}

		String perspId = null;
		String[] commandLineArgs = Platform.getCommandLineArgs();
		for (int i = 0; i < commandLineArgs.length - 1; i++) {
			if (commandLineArgs[i].equalsIgnoreCase("-perspective")) { //$NON-NLS-1$
				perspId = commandLineArgs[i + 1];
				break;
			}
		}
		if (perspId == null) {
			return;
		}
		IPerspectiveDescriptor desc = getPerspectiveRegistry().findPerspectiveWithId(perspId);
		if (desc == null) {
			return;
		}

		IWorkbenchWindow win = getActiveWorkbenchWindow();
		if (win == null) {
			win = getWorkbenchWindows()[0];
		}

		final String threadPerspId = perspId;
		final IWorkbenchWindow threadWin = win;
		StartupThreading.runWithoutExceptions(new StartupRunnable() {
			@Override
			public void runWithException() throws Throwable {
				try {
					showPerspective(threadPerspId, threadWin);
				} catch (WorkbenchException e) {
					String msg = "Workbench exception showing specified command line perspective on startup."; //$NON-NLS-1$
					WorkbenchPlugin.log(msg, new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, 0,
							msg, e));
				}
			}
		});
	}

		final boolean showProgress = PrefUtil.getAPIPreferenceStore().getBoolean(
				IWorkbenchPreferenceConstants.SHOW_PROGRESS_ON_STARTUP);

		if (!showProgress) {
			doOpenFirstTimeWindow();
		} else {
			final int expectedProgressCount = Math.max(1, WorkbenchPlugin.getDefault()
					.getBundleCount() / 10);

			runStartupWithProgress(expectedProgressCount, new Runnable() {
				@Override
				public void run() {
					doOpenFirstTimeWindow();
				}
			});
		}
	}

	private void doOpenFirstTimeWindow() {
		try {
			final IAdaptable input[] = new IAdaptable[1];
			StartupThreading.runWithoutExceptions(new StartupRunnable() {

				@Override
				public void runWithException() throws Throwable {
					input[0] = getDefaultPageInput();
				}
			});

			openWorkbenchWindow(getDefaultPerspectiveId(), input[0]);
		} catch (final WorkbenchException e) {
			StartupThreading.runWithoutExceptions(new StartupRunnable() {

				@Override
				public void runWithException() throws Throwable {
					ErrorDialog.openError(null, WorkbenchMessages.Problems_Opening_Page, e
							.getMessage(), e.getStatus());
				}
			});
		}
	}

	private void runStartupWithProgress(final int expectedProgressCount, final Runnable runnable) {
		progressCount = 0;
		final double cutoff = 0.95;

		AbstractSplashHandler handler = getSplash();
		IProgressMonitor progressMonitor = null;
		if (handler != null)
			progressMonitor = handler.getBundleProgressMonitor();

		if (progressMonitor == null) {
			runnable.run();
		} else {
			progressMonitor.beginTask("", expectedProgressCount); //$NON-NLS-1$
			SynchronousBundleListener bundleListener = new StartupProgressBundleListener(
					progressMonitor, (int) (expectedProgressCount * cutoff));
			WorkbenchPlugin.getDefault().addBundleListener(bundleListener);
			try {
				runnable.run();
				progressMonitor.subTask(WorkbenchMessages.Startup_Done);
				int remainingWork = expectedProgressCount
						- Math.min(progressCount, (int) (expectedProgressCount * cutoff));
				progressMonitor.worked(remainingWork);
				progressMonitor.done();
			} finally {
				WorkbenchPlugin.getDefault().removeBundleListener(bundleListener);
			}
		}
	}

	@Override
	public IWorkbenchWindow openWorkbenchWindow(IAdaptable input) throws WorkbenchException {
		return openWorkbenchWindow(getDefaultPerspectiveId(), input);
	}

	@Override
	public IWorkbenchWindow openWorkbenchWindow(String perspectiveId, IAdaptable input)
			throws WorkbenchException {
		IPerspectiveDescriptor descriptor = getPerspectiveRegistry().findPerspectiveWithId(
				perspectiveId);
		try {
			MWindow window = BasicFactoryImpl.eINSTANCE.createTrimmedWindow();
			return openWorkbenchWindow(input, descriptor, window, true);
		} catch (InjectionException e) {
			throw new WorkbenchException(e.getMessage(), e);
		}
	}

	public WorkbenchWindow openWorkbenchWindow(IAdaptable input, IPerspectiveDescriptor descriptor,
			MWindow window, boolean newWindow) {
		return (WorkbenchWindow) createWorkbenchWindow(input, descriptor, window, newWindow);
	}

	@Override
	public boolean restart() {
		return close(PlatformUI.RETURN_RESTART, false);
	}

	@Override
	public boolean restart(boolean useCurrrentWorkspace) {
		if (useCurrrentWorkspace) {
			URL instanceUrl = Platform.getInstanceLocation().getURL();
			if (instanceUrl != null) {
				try {
					URI uri = instanceUrl.toURI();
					String command_line = buildCommandLine(uri.toString());
					if (command_line != null) {
						System.setProperty(PROP_EXIT_CODE, IApplication.EXIT_RELAUNCH.toString());
						System.setProperty(IApplicationContext.EXIT_DATA_PROPERTY, command_line);
					}
				} catch (URISyntaxException e) {
				}
			}
		}
		return close(PlatformUI.RETURN_RESTART, false);
	}

	private String buildCommandLine(String workspace) {
		String property = System.getProperty(PROP_VM);
		if (property == null) {
			if (!Platform.inDevelopmentMode()) {
				WorkbenchPlugin.log(NLS.bind(WorkbenchMessages.Workbench_missingPropertyMessage, PROP_VM));
			}
			return null;
		}

		StringBuffer result = new StringBuffer(512);
		result.append(property);
		result.append('\n');

		String vmargs = System.getProperty(PROP_VMARGS);
		if (vmargs != null) {
			result.append(vmargs);
		}

		property = System.getProperty(PROP_COMMANDS);
		if (property == null) {
			result.append(CMD_DATA);
			result.append('\n');
			result.append(workspace);
			result.append('\n');
		} else {
			int cmd_data_pos = property.lastIndexOf(CMD_DATA);
			if (cmd_data_pos != -1) {
				cmd_data_pos += CMD_DATA.length() + 1;
				result.append(property.substring(0, cmd_data_pos));
				result.append(workspace);
				int nextArg = property.indexOf("\n-", cmd_data_pos - 1); //$NON-NLS-1$
				if (nextArg != -1) {
					result.append(property.substring(nextArg));
				}
			} else {
				result.append(CMD_DATA);
				result.append('\n');
				result.append(workspace);
				result.append('\n');
				result.append(property);
			}
		}

		if (vmargs != null) {
			if (result.charAt(result.length() - 1) != '\n') {
				result.append('\n');
			}
			result.append(CMD_VMARGS);
			result.append('\n');
			result.append(vmargs);
		}

		return result.toString();
	}

	public ContributionInfo[] getEarlyActivatedPlugins() {
		IExtensionPoint point = Platform.getExtensionRegistry().getExtensionPoint(
				PlatformUI.PLUGIN_ID, IWorkbenchRegistryConstants.PL_STARTUP);
		IExtension[] extensions = point.getExtensions();
		ArrayList<String> pluginIds = new ArrayList<String>(extensions.length);
		for (int i = 0; i < extensions.length; i++) {
			String id = extensions[i].getNamespaceIdentifier();
			if (!pluginIds.contains(id)) {
				pluginIds.add(id);
			}
		}
		ContributionInfo[] result = new ContributionInfo[pluginIds.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = new ContributionInfo(pluginIds.get(i),
					ContributionInfoMessages.ContributionInfo_EarlyStartupPlugin, null);

		}
		return result;
	}

	public String[] getDisabledEarlyActivatedPlugins() {
		String pref = PrefUtil.getInternalPreferenceStore().getString(
				IPreferenceConstants.PLUGINS_NOT_ACTIVATED_ON_STARTUP);
		return Util.getArrayFromList(pref, ";"); //$NON-NLS-1$
	}

	private void startPlugins() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();

		IExtensionPoint point = registry.getExtensionPoint(PlatformUI.PLUGIN_ID,
				IWorkbenchRegistryConstants.PL_STARTUP);

		final IExtension[] extensions = point.getExtensions();
		if (extensions.length == 0) {
			return;
		}
		Job job = new Job("Workbench early startup") { //$NON-NLS-1$
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				HashSet disabledPlugins = new HashSet(Arrays
						.asList(getDisabledEarlyActivatedPlugins()));
				monitor.beginTask(WorkbenchMessages.Workbench_startingPlugins, extensions.length);
				for (int i = 0; i < extensions.length; ++i) {
					if (monitor.isCanceled() || !isRunning()) {
						return Status.CANCEL_STATUS;
					}
					IExtension extension = extensions[i];

					if (!disabledPlugins.contains(extension.getNamespace())) {
						monitor.subTask(extension.getNamespace());
						SafeRunner.run(new EarlyStartupRunnable(extension));
					}
					monitor.worked(1);
				}
				monitor.done();
				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				return EARLY_STARTUP_FAMILY.equals(family);
			}
		};
		job.setSystem(true);
		job.schedule();
	}

	public void setEnableAutoSave(boolean b) {
		workbenchAutoSave = b;
	}

	private volatile boolean initDone = false;

	private int runUI() {
		UIStats.start(UIStats.START_WORKBENCH, "Workbench"); //$NON-NLS-1$

		boolean avoidDeadlock = true;

		String[] commandLineArgs = Platform.getCommandLineArgs();
		for (int i = 0; i < commandLineArgs.length; i++) {
			if (commandLineArgs[i].equalsIgnoreCase("-allowDeadlock")) { //$NON-NLS-1$
				avoidDeadlock = false;
			}
		}

		final UISynchronizer synchronizer;

		if (avoidDeadlock) {
			UILockListener uiLockListener = new UILockListener(display);
			Job.getJobManager().setLockListener(uiLockListener);
			synchronizer = new UISynchronizer(display, uiLockListener);
			display.setSynchronizer(synchronizer);
			UISynchronizer.startupThread.set(Boolean.TRUE);
		} else
			synchronizer = null;


		ModalContext.setAllowReadAndDispatch(false);

		if (WorkbenchPlugin.getDefault().isDebugging()) {
			display.asyncExec(new Runnable() {
				@Override
				public void run() {
					if (isStarting()) {
						WorkbenchPlugin.log(StatusUtil.newStatus(IStatus.WARNING,
								"Event loop should not be run while the Workbench is starting.", //$NON-NLS-1$
								new RuntimeException()));
					}
				}
			});
		}

		Listener closeListener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				event.doit = close();
			}
		};

		Window.IExceptionHandler handler = ExceptionHandler.getInstance();

		try {
			display.addListener(SWT.Close, closeListener);

			Window.setExceptionHandler(handler);

			final boolean[] initOK = new boolean[1];

			if (getSplash() != null) {

				final Throwable[] error = new Throwable[1];
				Thread initThread = new Thread() {
					@Override
					public void run() {
						try {
							UISynchronizer.startupThread.set(Boolean.TRUE);
							initOK[0] = Workbench.this.init();
						} catch (Throwable e) {
							error[0] = e;
						} finally {
							initDone = true;
							yield();
							try {
								Thread.sleep(5);
							} catch (InterruptedException e) {
							}
							display.wake();
						}
					}
				};
				initThread.start();
				while (true) {
					if (!display.readAndDispatch()) {
						if (initDone)
							break;
						display.sleep();
					}
				}
				Throwable throwable = error[0];
				if (throwable != null) {
					if (throwable instanceof Error)
						throw (Error) throwable;
					if (throwable instanceof Exception)
						throw (Exception) throwable;

					throw new Error(throwable);
				}
			} else {
				initOK[0] = init();

			}

			if (initOK[0] && runEventLoop) {
				Hashtable<String, Object> properties = new Hashtable<String, Object>();
				properties.put("id", getId()); //$NON-NLS-1$

				workbenchService = WorkbenchPlugin.getDefault().getBundleContext()
						.registerService(IWorkbench.class.getName(), this, properties);

				e4WorkbenchService = WorkbenchPlugin.getDefault().getBundleContext()
						.registerService(org.eclipse.e4.ui.workbench.IWorkbench.class.getName(),
								this, properties);

				Runnable earlyStartup = new Runnable() {
					@Override
					public void run() {
						advisor.postStartup(); // May trigger a close/restart.
						startPlugins();
						addStartupRegistryListener();
					}
				};
				e4Context.set(PartRenderingEngine.EARLY_STARTUP_HOOK, earlyStartup);
				final int millisecondInterval = getAutoSaveJobTime();
				if (millisecondInterval > 0 && workbenchAutoSave) {
					autoSaveJob = new WorkbenchJob(WORKBENCH_AUTO_SAVE_JOB) {
						@Override
						public IStatus runInUIThread(IProgressMonitor monitor) {
							if (monitor.isCanceled()) {
								return Status.CANCEL_STATUS;
							}
							final int nextDelay = getAutoSaveJobTime();
							try {
								persist(false);
								monitor.done();
							} finally {
								if (nextDelay > 0 && workbenchAutoSave) {
									this.schedule(nextDelay);
								}
							}
							return Status.OK_STATUS;
						}

					};
					autoSaveJob.setSystem(true);
					autoSaveJob.schedule(millisecondInterval);
				}


				display.asyncExec(new Runnable() {
					@Override
					public void run() {
						UIStats.end(UIStats.START_WORKBENCH, this, "Workbench"); //$NON-NLS-1$
						UIStats.startupComplete();
					}
				});

				getWorkbenchTestable().init(display, this);

				ModalContext.setAllowReadAndDispatch(true);
				isStarting = false;

				if (synchronizer != null)
					synchronizer.started();
			}
			returnCode = PlatformUI.RETURN_OK;
			if (!initOK[0]) {
				returnCode = PlatformUI.RETURN_UNSTARTABLE;
			}
		} catch (final Exception e) {
			if (!display.isDisposed()) {
				handler.handleException(e);
			} else {
				String msg = "Exception in Workbench.runUI after display was disposed"; //$NON-NLS-1$
				WorkbenchPlugin.log(msg, new Status(IStatus.ERROR, WorkbenchPlugin.PI_WORKBENCH, 1,
						msg, e));
			}
		}

		return returnCode;
	}

	private int getAutoSaveJobTime() {
		final int minuteSaveInterval = getPreferenceStore().getInt(
				IPreferenceConstants.WORKBENCH_SAVE_INTERVAL);
		final int millisecondInterval = minuteSaveInterval * 60 * 1000;
		return millisecondInterval;
	}



	@Override
	public IWorkbenchPage showPerspective(String perspectiveId, IWorkbenchWindow window)
			throws WorkbenchException {
		return showPerspective(perspectiveId, window, advisor.getDefaultPageInput());
	}

	private boolean activate(String perspectiveId, IWorkbenchPage page, IAdaptable input) {
		if (page != null) {
			for (IPerspectiveDescriptor openedPerspective : page.getOpenPerspectives()) {
				if (openedPerspective.getId().equals(perspectiveId)) {
					if (page.getInput() == input) {
						WorkbenchWindow wwindow = (WorkbenchWindow) page.getWorkbenchWindow();
						MWindow model = wwindow.getModel();
						application.setSelectedElement(model);
						page.setPerspective(openedPerspective);
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public IWorkbenchPage showPerspective(String perspectiveId, IWorkbenchWindow targetWindow,
			IAdaptable input) throws WorkbenchException {
		Assert.isNotNull(perspectiveId);
		IPerspectiveDescriptor targetPerspective = getPerspectiveRegistry().findPerspectiveWithId(
				perspectiveId);
		if (targetPerspective == null) {
			throw new WorkbenchException(NLS.bind(
					WorkbenchMessages.WorkbenchPage_ErrorCreatingPerspective, perspectiveId));
		}

		if (targetWindow != null) {
			IWorkbenchPage page = targetWindow.getActivePage();
			if (activate(perspectiveId, page, input)) {
				return page;
			}
		}

		for (IWorkbenchWindow window : getWorkbenchWindows()) {
			IWorkbenchPage page = window.getActivePage();
			if (activate(perspectiveId, page, input)) {
				return page;
			}
		}

		if (targetWindow != null) {
			IWorkbenchPage page = targetWindow.getActivePage();
			IPreferenceStore store = WorkbenchPlugin.getDefault().getPreferenceStore();
			int mode = store.getInt(IPreferenceConstants.OPEN_PERSP_MODE);

			if (IPreferenceConstants.OPM_NEW_WINDOW != mode) {
				targetWindow.getShell().open();
				if (page == null) {
					page = targetWindow.openPage(perspectiveId, input);
				} else {
					page.setPerspective(targetPerspective);
				}
				return page;
			}
		}

		return openWorkbenchWindow(perspectiveId, input).getActivePage();
	}

	private void shutdown() {
		try {
			advisor.postShutdown();
		} catch (Exception ex) {
			StatusManager.getManager().handle(
					StatusUtil.newStatus(WorkbenchPlugin.PI_WORKBENCH,
							"Exceptions during shutdown", ex)); //$NON-NLS-1$
		}

		firePostShutdown();
		workbenchListeners.clear();

		cancelEarlyStartup();
		if (workbenchService != null)
			workbenchService.unregister();
		workbenchService = null;

		if (e4WorkbenchService != null)
			e4WorkbenchService.unregister();
		e4WorkbenchService = null;

		Platform.getExtensionRegistry().removeRegistryChangeListener(extensionEventHandler);
		Platform.getExtensionRegistry().removeRegistryChangeListener(startupRegistryListener);

		((GrabFocus) Tweaklets.get(GrabFocus.KEY)).dispose();

		serviceLocator.dispose();
		application.getCommands().removeAll(commandsToRemove);
		application.getCategories().removeAll(categoriesToRemove);
		getDisplay().removeFilter(SWT.MouseDown, backForwardListener);
		backForwardListener = null;

		workbenchActivitySupport.dispose();
		WorkbenchHelpSystem.disposeIfNecessary();

		WorkbenchColors.shutdown();
		activityHelper.shutdown();
		uninitializeImages();
		if (WorkbenchPlugin.getDefault() != null) {
			WorkbenchPlugin.getDefault().reset();
		}
		WorkbenchThemeManager.getInstance().dispose();
		PropertyPageContributorManager.getManager().dispose();
		ObjectActionContributorManager.getManager().dispose();
		if (tracker != null) {
			tracker.close();
		}
	}

	private void cancelEarlyStartup() {
		Job.getJobManager().cancel(EARLY_STARTUP_FAMILY);
	}

	@Override
	public IDecoratorManager getDecoratorManager() {
		return WorkbenchPlugin.getDefault().getDecoratorManager();
	}

	WorkbenchConfigurer getWorkbenchConfigurer() {
		if (workbenchConfigurer == null) {
			workbenchConfigurer = new WorkbenchConfigurer();
		}
		return workbenchConfigurer;
	}

	WorkbenchAdvisor getAdvisor() {
		return advisor;
	}

	@Override
	public Display getDisplay() {
		return display;
	}

	public String getDefaultPerspectiveId() {
		return getAdvisor().getInitialWindowPerspectiveId();
	}

	public IAdaptable getDefaultPageInput() {
		return getAdvisor().getDefaultPageInput();
	}

	public String getMainPreferencePageId() {
		String id = getAdvisor().getMainPreferencePageId();
		return id;
	}

	@Override
	public IElementFactory getElementFactory(String factoryId) {
		Assert.isNotNull(factoryId);
		return WorkbenchPlugin.getDefault().getElementFactory(factoryId);
	}

	@Override
	public IProgressService getProgressService() {
		return e4Context.get(IProgressService.class);
	}

	private WorkbenchActivitySupport workbenchActivitySupport;

	private WorkbenchCommandSupport workbenchCommandSupport;

	private WorkbenchContextSupport workbenchContextSupport;

	private BindingManager bindingManager;

	private CommandManager commandManager;

	private ContextManager contextManager;

	@Override
	public IWorkbenchActivitySupport getActivitySupport() {
		return e4Context.get(IWorkbenchActivitySupport.class);
	}

	@Override
	public IWorkbenchCommandSupport getCommandSupport() {
		return workbenchCommandSupport;
	}

	@Override
	public IWorkbenchContextSupport getContextSupport() {
		return workbenchContextSupport;
	}

	public ContextManager getContextManager() {
		return contextManager;
	}

	private final IBindingManagerListener bindingManagerListener = new IBindingManagerListener() {

		@Override
		public void bindingManagerChanged(BindingManagerEvent bindingManagerEvent) {
			if (bindingManagerEvent.isActiveBindingsChanged()) {
				updateActiveWorkbenchWindowMenuManager(true);
			}
		}
	};

	private void updateActiveWorkbenchWindowMenuManager(boolean textOnly) {

		final IWorkbenchWindow workbenchWindow = getActiveWorkbenchWindow();

		if (workbenchWindow instanceof WorkbenchWindow) {
			WorkbenchWindow activeWorkbenchWindow = (WorkbenchWindow) workbenchWindow;
			if (activeWorkbenchWindow.isClosing()) {
				return;
			}

			final MenuManager menuManager = activeWorkbenchWindow.getMenuManager();

			if (textOnly) {
				menuManager.update(IAction.TEXT);
			} else {
				menuManager.update(true);
			}
		}
	}

	private ActivityPersistanceHelper activityHelper;

	@Override
	public IIntroManager getIntroManager() {
		return getWorkbenchIntroManager();
	}

		if (introManager == null) {
			introManager = new WorkbenchIntroManager(this);
		}
		return introManager;
	}

	private WorkbenchIntroManager introManager;

	public IntroDescriptor getIntroDescriptor() {
		return introDescriptor;
	}

	public void setIntroDescriptor(IntroDescriptor descriptor) {
		if (getIntroManager().getIntro() != null) {
			getIntroManager().closeIntro(getIntroManager().getIntro());
		}
		introDescriptor = descriptor;
	}

	private IntroDescriptor introDescriptor;

	private IExtensionTracker tracker;

	private IRegistryChangeListener startupRegistryListener = new IRegistryChangeListener() {

		@Override
		public void registryChanged(IRegistryChangeEvent event) {
			final IExtensionDelta[] deltas = event.getExtensionDeltas(PlatformUI.PLUGIN_ID,
					IWorkbenchRegistryConstants.PL_STARTUP);
			if (deltas.length == 0) {
				return;
			}
			final String disabledPlugins = PrefUtil.getInternalPreferenceStore().getString(
					IPreferenceConstants.PLUGINS_NOT_ACTIVATED_ON_STARTUP);

			for (int i = 0; i < deltas.length; i++) {
				IExtension extension = deltas[i].getExtension();
				if (deltas[i].getKind() == IExtensionDelta.REMOVED) {
					continue;
				}

				if (disabledPlugins.indexOf(extension.getNamespace()) == -1) {
					SafeRunner.run(new EarlyStartupRunnable(extension));
				}
			}

		}
	};

	@Override
	public IThemeManager getThemeManager() {
		return WorkbenchThemeManager.getInstance();
	}

	public boolean isRunning() {
		return runEventLoop;
	}

	public final void largeUpdateStart() {
		if (largeUpdates++ == 0) {

			final IWorkbenchWindow[] windows = getWorkbenchWindows();
			for (int i = 0; i < windows.length; i++) {
				IWorkbenchWindow window = windows[i];
				if (window instanceof WorkbenchWindow) {
					((WorkbenchWindow) window).largeUpdateStart();
				}
			}
		}
	}

	public final void largeUpdateEnd() {
		if (--largeUpdates == 0) {

			final IWorkbenchWindow[] windows = getWorkbenchWindows();
			for (int i = 0; i < windows.length; i++) {
				IWorkbenchWindow window = windows[i];
				if (window instanceof WorkbenchWindow) {
					((WorkbenchWindow) window).largeUpdateEnd();
				}
			}
		}
	}

	@Override
	public IExtensionTracker getExtensionTracker() {
		return e4Context.get(IExtensionTracker.class);
	}

	private void addStartupRegistryListener() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		registry.addRegistryChangeListener(startupRegistryListener);
	}

	@Override
	public IWorkbenchHelpSystem getHelpSystem() {
		return WorkbenchHelpSystem.getInstance();
	}

	@Override
	public IWorkbenchBrowserSupport getBrowserSupport() {
		return WorkbenchBrowserSupport.getInstance();
	}

	@Override
	public IViewRegistry getViewRegistry() {
		return WorkbenchPlugin.getDefault().getViewRegistry();
	}

	@Override
	public IWizardRegistry getNewWizardRegistry() {
		return WorkbenchPlugin.getDefault().getNewWizardRegistry();
	}

	@Override
	public IWizardRegistry getImportWizardRegistry() {
		return WorkbenchPlugin.getDefault().getImportWizardRegistry();
	}

	@Override
	public IWizardRegistry getExportWizardRegistry() {
		return WorkbenchPlugin.getDefault().getExportWizardRegistry();
	}

	@Override
	public final Object getAdapter(final Class key) {
		return serviceLocator.getService(key);
	}


	@Override
	public final Object getService(final Class key) {
		return serviceLocator.getService(key);
	}

	@Override
	public final boolean hasService(final Class key) {
		return serviceLocator.hasService(key);
	}

	public final void registerService(final Class api, final Object service) {
		serviceLocator.registerService(api, service);
	}

	private MenuSourceProvider menuSourceProvider;

	public final void addShowingMenus(final Set menuIds, final ISelection localSelection,
			final ISelection localEditorInput) {
		menuSourceProvider.addShowingMenus(menuIds, localSelection, localEditorInput);
		Map currentState = menuSourceProvider.getCurrentState();
		for (String key : menuSourceProvider.getProvidedSourceNames()) {
			e4Context.set(key, currentState.get(key));
		}
	}

	public final void removeShowingMenus(final Set menuIds, final ISelection localSelection,
			final ISelection localEditorInput) {
		menuSourceProvider.removeShowingMenus(menuIds, localSelection, localEditorInput);
		for (String key : menuSourceProvider.getProvidedSourceNames()) {
			e4Context.remove(key);
		}
	}

	@Override
	public boolean saveAll(final IShellProvider shellProvider,
			final IRunnableContext runnableContext, final ISaveableFilter filter, boolean confirm) {
		SaveablesList saveablesList = (SaveablesList) getService(ISaveablesLifecycleListener.class);
		Saveable[] saveables = saveablesList.getOpenModels();
		List<Saveable> toSave = getFilteredSaveables(filter, saveables);
		if (toSave.isEmpty()) {
			return true;
		}

		if (!confirm) {
			return !saveablesList.saveModels(toSave, shellProvider, runnableContext);
		}

		return !saveablesList.promptForSaving(toSave, shellProvider, runnableContext, true, false);
	}

	private List<Saveable> getFilteredSaveables(ISaveableFilter filter, Saveable[] saveables) {
		List<Saveable> toSave = new ArrayList<Saveable>();
		if (filter == null) {
			for (int i = 0; i < saveables.length; i++) {
				Saveable saveable = saveables[i];
				if (saveable.isDirty()) {
					toSave.add(saveable);
				}
			}
		} else {
			SaveablesList saveablesList = (SaveablesList) getService(ISaveablesLifecycleListener.class);
			for (int i = 0; i < saveables.length; i++) {
				Saveable saveable = saveables[i];
				if (saveable.isDirty()) {
					IWorkbenchPart[] parts = saveablesList.getPartsForSaveable(saveable);
					if (matchesFilter(filter, saveable, parts)) {
						toSave.add(saveable);
					}
				}
			}
		}
		return toSave;
	}

	private boolean matchesFilter(ISaveableFilter filter, Saveable saveable, IWorkbenchPart[] parts) {
		return filter == null || filter.select(saveable, parts);
	}

	public ServiceLocator getServiceLocator() {
		return serviceLocator;
	}

	@Override
	public IShellProvider getModalDialogShellProvider() {
		return new IShellProvider() {
			@Override
			public Shell getShell() {
				return ProgressManagerUtil.getDefaultParent();
			}
		};
	}

	public IEclipseContext getContext() {
		return e4Context;
	}

	@Override
	public MApplication getApplication() {
		return application;
	}

	private void persistWorkbenchState() {
		try {
			XMLMemento memento = XMLMemento.createWriteRoot(IWorkbenchConstants.TAG_WORKBENCH);
			IStatus status = saveWorkbenchState(memento);

			if (status.getSeverity() == IStatus.OK) {
				StringWriter writer = new StringWriter();
				memento.save(writer);
				application.getPersistedState().put(MEMENTO_KEY, writer.toString());
			} else {
				WorkbenchPlugin.log(new Status(status.getSeverity(), PlatformUI.PLUGIN_ID,
						WorkbenchMessages.Workbench_problemsSavingMsg));
			}
		} catch (IOException e) {
			WorkbenchPlugin.log(new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, 0,
					WorkbenchMessages.Workbench_problemsSavingMsg, e));
		}
	}

	private IStatus saveWorkbenchState(IMemento memento) {
		MultiStatus result = new MultiStatus(PlatformUI.PLUGIN_ID, IStatus.OK,
				WorkbenchMessages.Workbench_problemsSaving, null);


		result.add(getEditorHistory().saveState(
				memento.createChild(IWorkbenchConstants.TAG_MRU_LIST)));
		return result;
	}

	private void restoreWorkbenchState() {
		try {
			String persistedState = application.getPersistedState().get(MEMENTO_KEY);
			if (persistedState != null) {
				XMLMemento memento = XMLMemento.createReadRoot(new StringReader(persistedState));
				IStatus status = readWorkbenchState(memento);

				if (status.getSeverity() != IStatus.OK) {
					WorkbenchPlugin.log(new Status(status.getSeverity(), PlatformUI.PLUGIN_ID,
							WorkbenchMessages.Workbench_problemsRestoring));
				}
			}
		} catch (Exception e) {
			WorkbenchPlugin.log(new Status(
					IStatus.ERROR, PlatformUI.PLUGIN_ID, 0,
					WorkbenchMessages.Workbench_problemsRestoring, e));
		}
	}

	private IStatus readWorkbenchState(IMemento memento) {
		MultiStatus result = new MultiStatus(PlatformUI.PLUGIN_ID, IStatus.OK,
				WorkbenchMessages.Workbench_problemsRestoring, null);

		try {
			UIStats.start(UIStats.RESTORE_WORKBENCH, "MRUList"); //$NON-NLS-1$
			IMemento mruMemento = memento.getChild(IWorkbenchConstants.TAG_MRU_LIST);
			if (mruMemento != null) {
				result.add(getEditorHistory().restoreState(mruMemento));
			}
		} finally {
			UIStats.end(UIStats.RESTORE_WORKBENCH, this, "MRUList"); //$NON-NLS-1$
		}
		return result;
	}

	@Override
	public final String getId() {
		return id;
	}

	protected String createId() {
		return UUID.randomUUID().toString();
	}
}
