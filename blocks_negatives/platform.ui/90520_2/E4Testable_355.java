/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Tristan Hume - <trishume@gmail.com> -
 *     		Fix for Bug 2369 [Workbench] Would like to be able to save workspace without exiting
 *     		Implemented workbench auto-save to correctly restore state in case of crash.
 *     Lars Vogel <Lars.Vogel@vogella.com> - Bug 366364, 445724, 446088, 458033, 393171
 *     Terry Parker <tparker@google.com> - Bug 416673
 *     Christian Georgi (SAP)            - Bug 432480
 *     Simon Scholz <simon.scholz@vogella.com> - Bug 478896
 ******************************************************************************/

package org.eclipse.e4.ui.internal.workbench.swt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.RegistryFactory;
import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.contexts.RunAndTrack;
import org.eclipse.e4.core.internal.services.EclipseAdapter;
import org.eclipse.e4.core.services.adapter.Adapter;
import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.eclipse.e4.core.services.log.ILoggerProvider;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.core.services.translation.TranslationProviderFactory;
import org.eclipse.e4.core.services.translation.TranslationService;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.internal.workbench.ActiveChildLookupFunction;
import org.eclipse.e4.ui.internal.workbench.ActivePartLookupFunction;
import org.eclipse.e4.ui.internal.workbench.DefaultLoggerProvider;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.internal.workbench.ExceptionHandler;
import org.eclipse.e4.ui.internal.workbench.ModelServiceImpl;
import org.eclipse.e4.ui.internal.workbench.PlaceholderResolver;
import org.eclipse.e4.ui.internal.workbench.ReflectionContributionFactory;
import org.eclipse.e4.ui.internal.workbench.ResourceHandler;
import org.eclipse.e4.ui.internal.workbench.SelectionAggregator;
import org.eclipse.e4.ui.internal.workbench.SelectionServiceImpl;
import org.eclipse.e4.ui.internal.workbench.URIHelper;
import org.eclipse.e4.ui.internal.workbench.WorkbenchLogger;
import org.eclipse.e4.ui.model.application.MAddon;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.basic.impl.BasicPackageImpl;
import org.eclipse.e4.ui.model.application.ui.impl.UiPackageImpl;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.services.IStylingEngine;
import org.eclipse.e4.ui.workbench.IExceptionHandler;
import org.eclipse.e4.ui.workbench.IModelResourceHandler;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.e4.ui.workbench.lifecycle.PreSave;
import org.eclipse.e4.ui.workbench.lifecycle.ProcessAdditions;
import org.eclipse.e4.ui.workbench.lifecycle.ProcessRemovals;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPlaceholderResolver;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.e4.ui.workbench.swt.internal.copy.WorkbenchSWTMessages;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.Bundle;
import org.w3c.dom.css.CSSStyleDeclaration;

/**
 *
 */
public class E4Application implements IApplication {



	private static final String APPLICATION_MODEL_PATH_DEFAULT = "Application.e4xmi";
	private static final String PERSPECTIVE_ARG_NAME = "perspective";
	private static final String SHOWLOCATION_ARG_NAME = "showLocation";
	private static final String DEFAULT_THEME_ID = "org.eclipse.e4.ui.css.theme.e4_default";
	public static final String HIGH_CONTRAST_THEME_ID = "org.eclipse.e4.ui.css.theme.high-contrast";

	private String[] args;

	private IModelResourceHandler handler;
	private Display display = null;
	private E4Workbench workbench = null;

	public static final String THEME_ID = "cssTheme";

	private Object lcManager;

	public Display getApplicationDisplay() {
		if (display == null) {
			display = Display.getDefault();
		}
		return display;
	}

	@Override
	public Object start(IApplicationContext applicationContext) throws Exception {
		IProduct product = Platform.getProduct();
		if (product != null && product.getName() != null) {
			Display.setAppName(product.getName());
		}
		Display display = getApplicationDisplay();
		Location instanceLocation = null;
		try {
			E4Workbench workbench = createE4Workbench(applicationContext, display);

			instanceLocation = (Location) workbench.getContext().get(E4Workbench.INSTANCE_LOCATION);
			Shell shell = display.getActiveShell();
			if (shell == null) {
				shell = new Shell();
				shell.setLocation(0, 10000);
			}
			if (!checkInstanceLocation(instanceLocation, shell, workbench.getContext()))
				return EXIT_OK;

			workbench.createAndRunUI(workbench.getApplication());

			saveModel();
			workbench.close();

			if (workbench.isRestart()) {
				return EXIT_RESTART;
			}

			return EXIT_OK;
		} finally {
			if (display != null)
				display.dispose();
			if (instanceLocation != null)
				instanceLocation.release();
		}
	}

	public void saveModel() {
		if (lcManager != null && workbench != null) {
			ContextInjectionFactory.invoke(lcManager, PreSave.class, workbench.getContext(), null);
		}

		try {
			if (!(handler instanceof ResourceHandler) || ((ResourceHandler) handler).hasTopLevelWindows()) {
				handler.save();
			} else {
				Logger logger = new WorkbenchLogger(PLUGIN_ID);
				logger.error(new Exception(), // log a stack trace for debugging
			}
		} catch (IOException e) {
			Logger logger = new WorkbenchLogger(PLUGIN_ID);
			logger.error(e, "Error saving the workbench model"); //$NON-NLS-1$
		}
	}

	public E4Workbench createE4Workbench(IApplicationContext applicationContext, final Display display) {
		args = (String[]) applicationContext.getArguments().get(IApplicationContext.APPLICATION_ARGS);

		IEclipseContext appContext = createDefaultContext();
		appContext.set(Display.class, display);
		appContext.set(Realm.class, DisplayRealm.getRealm(display));
		appContext.set(UISynchronize.class, new UISynchronize() {

			@Override
			public void syncExec(Runnable runnable) {
				if (display != null && !display.isDisposed()) {
					display.syncExec(runnable);
				}
			}

			@Override
			public void asyncExec(Runnable runnable) {
				if (display != null && !display.isDisposed()) {
					display.asyncExec(runnable);
				}
			}
		});
		appContext.set(IApplicationContext.class, applicationContext);

		ContextInjectionFactory.setDefault(appContext);

		IContributionFactory factory = appContext.get(IContributionFactory.class);

		Optional<String> lifeCycleURI = getArgValue(IWorkbench.LIFE_CYCLE_URI_ARG, applicationContext, false);
		lifeCycleURI.ifPresent(lifeCycleURIValue -> {
			lcManager = factory.create(lifeCycleURIValue, appContext);
			if (lcManager != null) {
				ContextInjectionFactory.invoke(lcManager, PostContextCreate.class, appContext, null);
			}
		});

		Optional<String> forcedPerspectiveId = getArgValue(PERSPECTIVE_ARG_NAME, applicationContext, false);
		forcedPerspectiveId.ifPresent(forcedPerspectiveIdValue -> appContext.set(E4Workbench.FORCED_PERSPECTIVE_ID,
				forcedPerspectiveIdValue));

		String showLocation = getLocationFromCommandLine();
		if (showLocation != null) {
			appContext.set(E4Workbench.FORCED_SHOW_LOCATION, showLocation);
		}

		MApplication appModel = loadApplicationModel(applicationContext, appContext);
		appModel.setContext(appContext);

		boolean isRtl = ((Window.getDefaultOrientation() & SWT.RIGHT_TO_LEFT) != 0);
		appModel.getTransientData().put(E4Workbench.RTL_MODE, isRtl);

		if (!E4Workbench.getServiceContext().containsKey(MApplication.class)) {
			E4Workbench.getServiceContext().set(MApplication.class, appModel);
		}

		appContext.set(MApplication.class, appModel);

		initializeServices(appModel);

		if (lcManager != null) {
			ContextInjectionFactory.invoke(lcManager, ProcessAdditions.class, appContext, null);
			ContextInjectionFactory.invoke(lcManager, ProcessRemovals.class, appContext, null);
		}

		IEclipseContext addonStaticContext = EclipseContextFactory.create();
		for (MAddon addon : appModel.getAddons()) {
			addonStaticContext.set(MAddon.class, addon);
			Object obj = factory.create(addon.getContributionURI(), appContext, addonStaticContext);
			addon.setObject(obj);
		}

		Optional<String> xmiURI = getArgValue(IWorkbench.XMI_URI_ARG, applicationContext, false);
		xmiURI.ifPresent(xmiURIValue -> {
			appContext.set(IWorkbench.XMI_URI_ARG, xmiURIValue);
		});


		setCSSContextVariables(applicationContext, appContext);

		Optional<String> rendererFactoryURI = getArgValue(E4Workbench.RENDERER_FACTORY_URI, applicationContext, false);
		rendererFactoryURI.ifPresent(rendererFactoryURIValue -> {
			appContext.set(E4Workbench.RENDERER_FACTORY_URI, rendererFactoryURIValue);
		});

		Optional<String> presentationURI = getArgValue(IWorkbench.PRESENTATION_URI_ARG, applicationContext, false);
		appContext.set(IWorkbench.PRESENTATION_URI_ARG, presentationURI.orElse(PartRenderingEngine.engineURI));

		return workbench = new E4Workbench(appModel, appContext);
	}

	private void setCSSContextVariables(IApplicationContext applicationContext, IEclipseContext context) {
		boolean highContrastMode = getApplicationDisplay().getHighContrast();

		Optional<String> cssURI = highContrastMode ? Optional.empty()
				: getArgValue(IWorkbench.CSS_URI_ARG, applicationContext, false);

		cssURI.ifPresent(cssURIValue -> {
			context.set(IWorkbench.CSS_URI_ARG, cssURIValue);
		});

		Optional<String> themeId = highContrastMode ? Optional.of(HIGH_CONTRAST_THEME_ID)
				: getArgValue(E4Application.THEME_ID, applicationContext, false);

		if (!themeId.isPresent() && !cssURI.isPresent()) {
			context.set(E4Application.THEME_ID, DEFAULT_THEME_ID);
		} else {
			context.set(E4Application.THEME_ID, themeId.orElseGet(() -> null));
		}


		cssURI.filter(cssURIValue -> !cssURIValue.startsWith("platform:/plugin/")).ifPresent(cssURIValue -> {
			System.err.println(
					"Warning. Use the \"platform:/plugin/Bundle-SymbolicName/path/filename.extension\" URI for the  parameter:   "
			context.set(E4Application.THEME_ID, cssURIValue);
		});

		Optional<String> cssResourcesURI = getArgValue(IWorkbench.CSS_RESOURCE_URI_ARG, applicationContext, false);
		cssResourcesURI.ifPresent(cssResourcesURIValue -> {
			context.set(IWorkbench.CSS_RESOURCE_URI_ARG, cssResourcesURIValue);
		});
	}

	private MApplication loadApplicationModel(IApplicationContext appContext, IEclipseContext eclipseContext) {
		MApplication theApp = null;

		Location instanceLocation = WorkbenchSWTActivator.getDefault().getInstanceLocation();

		URI applicationModelURI = determineApplicationModelURI(appContext);
		eclipseContext.set(E4Workbench.INITIAL_WORKBENCH_MODEL_URI, applicationModelURI);

		Boolean saveAndRestore = getArgValue(IWorkbench.PERSIST_STATE, appContext, false)
				.map(value -> Boolean.parseBoolean(value)).orElse(Boolean.TRUE);

		eclipseContext.set(IWorkbench.PERSIST_STATE, saveAndRestore);

		if (instanceLocation != null && instanceLocation.getURL() != null) {
			eclipseContext.set(E4Workbench.INSTANCE_LOCATION, instanceLocation);
		} else {
			eclipseContext.set(IWorkbench.PERSIST_STATE, false);
		}

		Boolean clearPersistedState = getArgValue(IWorkbench.CLEAR_PERSISTED_STATE, appContext, true)
				.map(value -> Boolean.parseBoolean(value)).orElse(Boolean.FALSE);
		eclipseContext.set(IWorkbench.CLEAR_PERSISTED_STATE, clearPersistedState);

		String resourceHandler = getArgValue(IWorkbench.MODEL_RESOURCE_HANDLER, appContext, false)

		IContributionFactory factory = eclipseContext.get(IContributionFactory.class);

		handler = (IModelResourceHandler) factory.create(resourceHandler, eclipseContext);
		eclipseContext.set(IModelResourceHandler.class, handler);

		Resource resource = handler.loadMostRecentModel();
		theApp = (MApplication) resource.getContents().get(0);

		return theApp;
	}

	/**
	 * @param appContext
	 * @return
	 */
	private URI determineApplicationModelURI(IApplicationContext appContext) {
		Optional<String> appModelPath = getArgValue(IWorkbench.XMI_URI_ARG, appContext, false);

		String appModelPathValue = appModelPath.filter(path -> !path.isEmpty()).orElseGet(() -> {
			Bundle brandingBundle = appContext.getBrandingBundle();
			if (brandingBundle != null) {
				return brandingBundle.getSymbolicName() + "/" + E4Application.APPLICATION_MODEL_PATH_DEFAULT;
			} else {
				Logger logger = new WorkbenchLogger(PLUGIN_ID);
				logger.error(new Exception(), "applicationXMI parameter not set and no branding plugin defined. "); //$NON-NLS-1$
			}
			return null;
		});

		URI applicationModelURI = null;

		if (URIHelper.isPlatformURI(appModelPathValue)) {
			applicationModelURI = URI.createURI(appModelPathValue, true);
		} else {
			applicationModelURI = URI.createPlatformPluginURI(appModelPathValue, true);
		}
		return applicationModelURI;

	}

	/**
	 * Finds an argument's value in the app's command line arguments, branding,
	 * and system properties
	 *
	 * @param argName
	 *            the argument name
	 * @param appContext
	 *            the application context
	 * @param singledCmdArgValue
	 *            whether it's a single-valued argument
	 * @return an {@link Optional} containing the value or an empty
	 *         {@link Optional}, if no value could be found
	 */
	private Optional<String> getArgValue(String argName, IApplicationContext appContext, boolean singledCmdArgValue) {
		if (argName == null || argName.length() == 0)
			return Optional.empty();

		if (singledCmdArgValue) {
			for (String arg : args) {
				if (("-" + argName).equals(arg))
					return Optional.of("true");
			}
		} else {
			for (int i = 0; i < args.length; i++) {
				if (("-" + argName).equals(args[i]) && i + 1 < args.length)
					return Optional.of(args[i + 1]);
			}
		}

		final String brandingProperty = appContext.getBrandingProperty(argName);

		return Optional.ofNullable(brandingProperty).map(brandingPropertyValue -> Optional.of(brandingPropertyValue))
				.orElse(Optional.ofNullable(System.getProperty(argName)));
	}

	/**
	 * @return the value of the {@link E4Application#SHOWLOCATION_ARG_NAME
	 *         showlocation} command line argument, or <code>null</code> if it
	 *         is not set
	 */
	private String getLocationFromCommandLine() {
		final String fullArgName = "-" + SHOWLOCATION_ARG_NAME;
		for (int i = 0; i < args.length; i++) {
				String name = null;
				if (args.length > i + 1) {
					name = args[i + 1];
				}
					return name;
				}
				return Platform.getLocation().toOSString();
			}
		}
		return null;
	}

	@Override
	public void stop() {
		if (workbench != null) {
			workbench.close();
		}
	}

	public static IEclipseContext createDefaultHeadlessContext() {
		IEclipseContext serviceContext = E4Workbench.getServiceContext();

		IExtensionRegistry registry = RegistryFactory.getRegistry();
		ExceptionHandler exceptionHandler = new ExceptionHandler();
		ReflectionContributionFactory contributionFactory = new ReflectionContributionFactory(registry);
		serviceContext.set(IContributionFactory.class, contributionFactory);
		serviceContext.set(IExceptionHandler.class, exceptionHandler);
		serviceContext.set(IExtensionRegistry.class, registry);

		serviceContext.set(Adapter.class, ContextInjectionFactory.make(EclipseAdapter.class, serviceContext));

		if (serviceContext.get(ILoggerProvider.class) == null) {
			serviceContext.set(ILoggerProvider.class,
					ContextInjectionFactory.make(DefaultLoggerProvider.class, serviceContext));
		}

		return serviceContext;
	}

	public static IEclipseContext createDefaultContext() {

		IEclipseContext serviceContext = createDefaultHeadlessContext();
		appContext.set(IWorkbench.APPLICATION_CONTEXT_KEY, appContext);

		appContext.set(Logger.class, ContextInjectionFactory.make(WorkbenchLogger.class, appContext));
		appContext.set(EModelService.class, new ModelServiceImpl(appContext));
		appContext.set(EPlaceholderResolver.class, new PlaceholderResolver());

		appContext.set(IServiceConstants.ACTIVE_PART, new ActivePartLookupFunction());

		appContext.set(IServiceConstants.ACTIVE_SHELL,
				new ActiveChildLookupFunction(IServiceConstants.ACTIVE_SHELL, E4Workbench.LOCAL_ACTIVE_SHELL));

		appContext.set(IStylingEngine.class, new IStylingEngine() {
			@Override
			public void setClassname(Object widget, String classname) {
			}

			@Override
			public void setId(Object widget, String id) {
			}

			@Override
			public void style(Object widget) {
			}

			@Override
			public CSSStyleDeclaration getStyle(Object widget) {
				return null;
			}

			@Override
			public void setClassnameAndId(Object widget, String classname, String id) {
			}
		});

		initializeLocalization(appContext);

		return appContext;
	}

	/**
	 * Initializes the given context with the locale and the TranslationService
	 * to use.
	 *
	 * @param appContext
	 *            The application context to which the locale and the
	 *            TranslationService should be set.
	 */
	private static void initializeLocalization(IEclipseContext appContext) {
		appContext.set(TranslationService.LOCALE, Locale.getDefault());
		appContext.set(TranslationService.class, TranslationProviderFactory.bundleTranslationService(appContext));
	}

	/**
	 * Simplified copy of IDEAplication processing that does not offer to choose
	 * a workspace location.
	 */
	private boolean checkInstanceLocation(Location instanceLocation, Shell shell, IEclipseContext context) {

		if (instanceLocation == null && Boolean.FALSE.equals(context.get(IWorkbench.PERSIST_STATE))) {
			return true;
		}

		if (instanceLocation == null) {
			MessageDialog.openError(shell, WorkbenchSWTMessages.IDEApplication_workspaceMandatoryTitle,
					WorkbenchSWTMessages.IDEApplication_workspaceMandatoryMessage);
			return false;
		}

		if (instanceLocation.isSet()) {
			if (!checkValidWorkspace(shell, instanceLocation.getURL())) {
				return false;
			}

			try {
				if (instanceLocation.lock()) {
					writeWorkspaceVersion();
					return true;
				}

				File workspaceDirectory = new File(instanceLocation.getURL().getFile());
				if (workspaceDirectory.exists()) {
					MessageDialog.openError(shell, WorkbenchSWTMessages.IDEApplication_workspaceCannotLockTitle,
							WorkbenchSWTMessages.IDEApplication_workspaceCannotLockMessage);
				} else {
					MessageDialog.openError(shell, WorkbenchSWTMessages.IDEApplication_workspaceCannotBeSetTitle,
							WorkbenchSWTMessages.IDEApplication_workspaceCannotBeSetMessage);
				}
			} catch (IOException e) {
				Logger logger = new WorkbenchLogger(PLUGIN_ID);
				logger.error(e);
				MessageDialog.openError(shell, WorkbenchSWTMessages.InternalError, e.getMessage());
			}
			return false;
		}
		return false;
	}

	/**
	 * Return true if the argument directory is ok to use as a workspace and
	 * false otherwise. A version check will be performed, and a confirmation
	 * box may be displayed on the argument shell if an older version is
	 * detected.
	 *
	 * @return true if the argument URL is ok to use as a workspace and false
	 *         otherwise.
	 */
	private boolean checkValidWorkspace(Shell shell, URL url) {
		if (url == null) {
			return false;
		}

		String version = readWorkspaceVersion(url);

		if (version == null) {
			return true;
		}

		final int ide_version = Integer.parseInt(WORKSPACE_VERSION_VALUE);
		int workspace_version = Integer.parseInt(version);

		if (workspace_version == ide_version) {
			return true;
		}

		String title = WorkbenchSWTMessages.IDEApplication_versionTitle;
		String message = NLS.bind(WorkbenchSWTMessages.IDEApplication_versionMessage, url.getFile());

		MessageBox mbox = new MessageBox(shell, SWT.OK | SWT.CANCEL | SWT.ICON_WARNING | SWT.APPLICATION_MODAL);
		mbox.setText(title);
		mbox.setMessage(message);
		return mbox.open() == SWT.OK;
	}

	/**
	 * Look at the argument URL for the workspace's version information. Return
	 * that version if found and null otherwise.
	 */
	private static String readWorkspaceVersion(URL workspace) {
		File versionFile = getVersionFile(workspace, false);
		if (versionFile == null || !versionFile.exists()) {
			return null;
		}

		try {
			Properties props = new Properties();
			FileInputStream is = new FileInputStream(versionFile);
			try {
				props.load(is);
			} finally {
				is.close();
			}

			return props.getProperty(WORKSPACE_VERSION_KEY);
		} catch (IOException e) {
			Logger logger = new WorkbenchLogger(PLUGIN_ID);
			logger.error(e);
			return null;
		}
	}

	/**
	 * Write the version of the metadata into a known file overwriting any
	 * existing file contents. Writing the version file isn't really crucial, so
	 * the function is silent about failure
	 */
	private static void writeWorkspaceVersion() {
		Location instanceLoc = Platform.getInstanceLocation();
		if (instanceLoc == null || instanceLoc.isReadOnly()) {
			return;
		}

		File versionFile = getVersionFile(instanceLoc.getURL(), true);
		if (versionFile == null) {
			return;
		}

		OutputStream output = null;
		try {
			String versionLine = WORKSPACE_VERSION_KEY + '=' + WORKSPACE_VERSION_VALUE;

			output = new FileOutputStream(versionFile);
			output.write(versionLine.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			Logger logger = new WorkbenchLogger(PLUGIN_ID);
			logger.error(e);
		} finally {
			try {
				if (output != null) {
					output.close();
				}
			} catch (IOException e) {
			}
		}
	}

	/**
	 * The version file is stored in the metadata area of the workspace. This
	 * method returns an URL to the file or null if the directory or file does
	 * not exist (and the create parameter is false).
	 *
	 * @param create
	 *            If the directory and file does not exist this parameter
	 *            controls whether it will be created.
	 * @return An url to the file or null if the version file does not exist or
	 *         could not be created.
	 */
	private static File getVersionFile(URL workspaceUrl, boolean create) {
		if (workspaceUrl == null) {
			return null;
		}

		try {
			File metaDir = new File(workspaceUrl.getPath(), METADATA_FOLDER);
			if (!metaDir.exists() && (!create || !metaDir.mkdir())) {
				return null;
			}

			File versionFile = new File(metaDir, VERSION_FILENAME);
			if (!versionFile.exists() && (!create || !versionFile.createNewFile())) {
				return null;
			}

			return versionFile;
		} catch (IOException e) {
			return null;
		}
	}

	static final private String CONTEXT_INITIALIZED = "org.eclipse.ui.contextInitialized";

	static public void initializeServices(MApplication appModel) {
		IEclipseContext appContext = appModel.getContext();
		if (appContext.containsKey(CONTEXT_INITIALIZED))
			return;
		appContext.set(CONTEXT_INITIALIZED, "true");
		initializeApplicationServices(appContext);
		List<MWindow> windows = appModel.getChildren();
		for (MWindow childWindow : windows) {
			initializeWindowServices(childWindow);
		}
		((EObject) appModel).eAdapters().add(new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				if (notification.getFeatureID(MApplication.class) != UiPackageImpl.ELEMENT_CONTAINER__CHILDREN)
					return;
				if (notification.getEventType() != Notification.ADD)
					return;
				MWindow childWindow = (MWindow) notification.getNewValue();
				initializeWindowServices(childWindow);
			}
		});
	}

	static public void initializeApplicationServices(IEclipseContext appContext) {
		final IEclipseContext theContext = appContext;
		appContext.runAndTrack(new RunAndTrack() {
			@Override
			public boolean changed(IEclipseContext context) {
				IEclipseContext activeChildContext = context.getActiveChild();
				if (activeChildContext != null) {
					Object selection = activeChildContext.get(IServiceConstants.ACTIVE_SELECTION);
					theContext.set(IServiceConstants.ACTIVE_SELECTION, selection);
				}
				return true;
			}
		});

		appContext.set(ESelectionService.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				return ContextInjectionFactory.make(SelectionServiceImpl.class, context);
			}
		});
	}

	static public void initializeWindowServices(MWindow childWindow) {
		IEclipseContext windowContext = childWindow.getContext();
		initWindowContext(windowContext);
		((EObject) childWindow).eAdapters().add(new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				if (notification.getFeatureID(MWindow.class) != BasicPackageImpl.WINDOW__CONTEXT)
					return;
				IEclipseContext windowContext = (IEclipseContext) notification.getNewValue();
				initWindowContext(windowContext);
			}
		});
	}

	static private void initWindowContext(IEclipseContext windowContext) {
		if (windowContext == null)
			return;
		SelectionAggregator selectionAggregator = ContextInjectionFactory.make(SelectionAggregator.class,
				windowContext);
		windowContext.set(SelectionAggregator.class, selectionAggregator);
	}
}
