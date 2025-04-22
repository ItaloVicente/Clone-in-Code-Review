
package org.eclipse.ui.internal;

import com.ibm.icu.text.MessageFormat;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.e4.core.commands.internal.ICommandHelpService;
import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.internal.workbench.IHelpService;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.util.BidiUtils;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.service.debug.DebugOptions;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IDecoratorManager;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkingSetManager;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.decorators.DecoratorManager;
import org.eclipse.ui.internal.dialogs.WorkbenchPreferenceManager;
import org.eclipse.ui.internal.help.CommandHelpServiceImpl;
import org.eclipse.ui.internal.help.HelpServiceImpl;
import org.eclipse.ui.internal.intro.IIntroRegistry;
import org.eclipse.ui.internal.intro.IntroRegistry;
import org.eclipse.ui.internal.misc.StatusUtil;
import org.eclipse.ui.internal.operations.WorkbenchOperationSupport;
import org.eclipse.ui.internal.progress.ProgressManager;
import org.eclipse.ui.internal.registry.ActionSetRegistry;
import org.eclipse.ui.internal.registry.EditorRegistry;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.registry.PerspectiveRegistry;
import org.eclipse.ui.internal.registry.PreferencePageRegistryReader;
import org.eclipse.ui.internal.registry.ViewRegistry;
import org.eclipse.ui.internal.registry.WorkingSetRegistry;
import org.eclipse.ui.internal.themes.IThemeRegistry;
import org.eclipse.ui.internal.themes.ThemeRegistry;
import org.eclipse.ui.internal.themes.ThemeRegistryReader;
import org.eclipse.ui.internal.util.BundleUtility;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.internal.wizards.ExportWizardRegistry;
import org.eclipse.ui.internal.wizards.ImportWizardRegistry;
import org.eclipse.ui.internal.wizards.NewWizardRegistry;
import org.eclipse.ui.operations.IWorkbenchOperationSupport;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.testing.TestableObject;
import org.eclipse.ui.views.IViewRegistry;
import org.eclipse.ui.wizards.IWizardRegistry;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleException;
import org.osgi.framework.BundleListener;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.SynchronousBundleListener;
import org.osgi.util.tracker.ServiceTracker;

public class WorkbenchPlugin extends AbstractUIPlugin {
	
	private static final String DATA_SPLASH_SHELL = "org.eclipse.ui.workbench.splashShell"; //$NON-NLS-1$

	private static final String PROP_SPLASH_HANDLE = "org.eclipse.equinox.launcher.splash.handle"; //$NON-NLS-1$
	
	private static final String LEFT_TO_RIGHT = "ltr"; //$NON-NLS-1$
	private static final String RIGHT_TO_LEFT = "rtl";//$NON-NLS-1$
	private static final String ORIENTATION_COMMAND_LINE = "-dir";//$NON-NLS-1$
	private static final String ORIENTATION_PROPERTY = "eclipse.orientation";//$NON-NLS-1$
	private static final String NL_USER_PROPERTY = "osgi.nl.user"; //$NON-NLS-1$
	private static final String BIDI_COMMAND_LINE = "-bidi";//$NON-NLS-1$	
	private static final String BIDI_SUPPORT_OPTION = "on";//$NON-NLS-1$
	private static final String BIDI_TEXTDIR_OPTION = "textDir";//$NON-NLS-1$

   
    private static WorkbenchPlugin inst;

    private EditorRegistry editorRegistry;

    private DecoratorManager decoratorManager;

    private ThemeRegistry themeRegistry;

    private WorkingSetManager workingSetManager;

    private WorkingSetRegistry workingSetRegistry;

    private BundleContext bundleContext;

	private Collection<Bundle> startingBundles = new HashSet<Bundle>();

    public static boolean DEBUG = false;

    public static String PI_WORKBENCH = PlatformUI.PLUGIN_ID;

    public static char PREFERENCE_PAGE_CATEGORY_SEPARATOR = '/';

    private WorkbenchPreferenceManager preferenceManager;

    private ViewRegistry viewRegistry;

    private PerspectiveRegistry perspRegistry;

    private ActionSetRegistry actionSetRegistry;

    private SharedImages sharedImages;

    private ProductInfo productInfo = null;

    private IntroRegistry introRegistry;
    
    private WorkbenchOperationSupport operationSupport;
	private BundleListener bundleListener;

	private IEclipseContext e4Context;

	private ServiceTracker debugTracker = null;
    
	private ServiceTracker testableTracker = null;
	
	private IHelpService helpService;

	private ICommandHelpService commandHelpService;

    public WorkbenchPlugin() {
        super();
        inst = this;
    }

    void reset() {
        editorRegistry = null;

        if (decoratorManager != null) {
            decoratorManager.dispose();
            decoratorManager = null;
        }

        ProgressManager.shutdownProgressManager();

        themeRegistry = null;
        if (workingSetManager != null) {
        	workingSetManager.dispose();
        	workingSetManager = null;
        }
        workingSetRegistry = null;

        preferenceManager = null;
        if (viewRegistry != null) {
            viewRegistry = null;
        }
        if (perspRegistry != null) {
            perspRegistry.dispose();
            perspRegistry = null;
        }
        actionSetRegistry = null;
        sharedImages = null;

        productInfo = null;
        introRegistry = null;

		helpService = null;
		commandHelpService = null;
        
        if (operationSupport != null) {
        	operationSupport.dispose();
        	operationSupport = null;
        }

        DEBUG = false;
         
    }

    public static Object createExtension(final IConfigurationElement element,
            final String classAttribute) throws CoreException {
        try {
			if (BundleUtility.isActivated(element.getContributor().getName())) {
                return element.createExecutableExtension(classAttribute);
            }
            final Object[] ret = new Object[1];
            final CoreException[] exc = new CoreException[1];
            BusyIndicator.showWhile(null, new Runnable() {
                @Override
				public void run() {
                    try {
                        ret[0] = element
                                .createExecutableExtension(classAttribute);
                    } catch (CoreException e) {
                        exc[0] = e;
                    }
                }
            });
            if (exc[0] != null) {
				throw exc[0];
			}
            return ret[0];

        } catch (CoreException core) {
            throw core;
        } catch (Exception e) {
            throw new CoreException(new Status(IStatus.ERROR, PI_WORKBENCH,
                    IStatus.ERROR, WorkbenchMessages.WorkbenchPlugin_extension,e));
        }
    }
    
	public static boolean hasExecutableExtension(IConfigurationElement element,
			String extensionName) {

		if (element.getAttribute(extensionName) != null)
			return true;
		String elementText = element.getValue();
		if (elementText != null && !elementText.equals("")) //$NON-NLS-1$
			return true;
		IConfigurationElement [] children = element.getChildren(extensionName);
		if (children.length == 1) {
			if (children[0].getAttribute(IWorkbenchRegistryConstants.ATT_CLASS) != null)
				return true;
		}
		return false;
	}
	
	public static boolean isBundleLoadedForExecutableExtension(
			IConfigurationElement element, String extensionName) {
		Bundle bundle = getBundleForExecutableExtension(element, extensionName);

		if (bundle == null)
			return true;
		return bundle.getState() == Bundle.ACTIVE;
	}
	
	public static Bundle getBundleForExecutableExtension(IConfigurationElement element, String extensionName) {
		String prop = null;
		String executable;
		String contributorName = null;
		int i;

		if (extensionName != null)
			prop = element.getAttribute(extensionName);
		else {
			prop = element.getValue();
			if (prop != null) {
				prop = prop.trim();
				if (prop.equals("")) //$NON-NLS-1$
					prop = null;
			}
		}

		if (prop == null) {
			IConfigurationElement[] exec = element.getChildren(extensionName);
			if (exec.length != 0) 
				contributorName = exec[0].getAttribute("plugin"); //$NON-NLS-1$
		} else {
			i = prop.indexOf(':');
			if (i != -1) 
				executable = prop.substring(0, i).trim();
			else
				executable = prop;

			i = executable.indexOf('/');
			if (i != -1)
				contributorName = executable.substring(0, i).trim();
				
		}
		
		if (contributorName == null)
			contributorName = element.getContributor().getName();
		
		return Platform.getBundle(contributorName);
	}

    @Override
	protected ImageRegistry createImageRegistry() {
        return WorkbenchImages.getImageRegistry();
    }

    public ActionSetRegistry getActionSetRegistry() {
		return e4Context.get(ActionSetRegistry.class);
    }

    public static WorkbenchPlugin getDefault() {
        return inst;
    }


    public IEditorRegistry getEditorRegistry() {
		return e4Context.get(IEditorRegistry.class);
    }

    public IElementFactory getElementFactory(String targetID) {

        IExtensionPoint extensionPoint;
        extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(
                PI_WORKBENCH, IWorkbenchRegistryConstants.PL_ELEMENT_FACTORY);

        if (extensionPoint == null) {
            WorkbenchPlugin
                    .log("Unable to find element factory. Extension point: " + IWorkbenchRegistryConstants.PL_ELEMENT_FACTORY + " not found"); //$NON-NLS-2$ //$NON-NLS-1$
            return null;
        }

        IConfigurationElement targetElement = null;
        IConfigurationElement[] configElements = extensionPoint
                .getConfigurationElements();
        for (int j = 0; j < configElements.length; j++) {
            String strID = configElements[j].getAttribute("id"); //$NON-NLS-1$
            if (targetID.equals(strID)) {
                targetElement = configElements[j];
                break;
            }
        }
        if (targetElement == null) {
            WorkbenchPlugin.log("Unable to find element factory: " + targetID); //$NON-NLS-1$
            return null;
        }

        IElementFactory factory = null;
        try {
            factory = (IElementFactory) createExtension(targetElement, "class"); //$NON-NLS-1$
        } catch (CoreException e) {
            WorkbenchPlugin.log(
                    "Unable to create element factory.", e.getStatus()); //$NON-NLS-1$
            factory = null;
        }
        return factory;
    }

    public IPerspectiveRegistry getPerspectiveRegistry() {
		return e4Context.get(IPerspectiveRegistry.class);
    }

    public IWorkingSetManager getWorkingSetManager() {
		return e4Context.get(IWorkingSetManager.class);
    }

    public WorkingSetRegistry getWorkingSetRegistry() {
		return e4Context.get(WorkingSetRegistry.class);
    }

    public IIntroRegistry getIntroRegistry() {
		return e4Context.get(IIntroRegistry.class);
    }
    
    public IWorkbenchOperationSupport getOperationSupport() {
		IWorkbenchOperationSupport op = e4Context.get(IWorkbenchOperationSupport.class);
		if (op == null) {
			op = new WorkbenchOperationSupport();
		}
		return op;
    }
    

    public PreferenceManager getPreferenceManager() {
		return e4Context.get(PreferenceManager.class);
    }

    public ISharedImages getSharedImages() {
    	if(sharedImages == null) {
    		sharedImages = new SharedImages();
    	}
    	if(e4Context == null) {
    		return sharedImages;
    	}
		return e4Context.get(ISharedImages.class);
    }

    public IThemeRegistry getThemeRegistry() {
		return e4Context.get(IThemeRegistry.class);
    }

    public IViewRegistry getViewRegistry() {
		return e4Context.get(IViewRegistry.class);
    }

    @Deprecated
	@Override
	public IWorkbench getWorkbench() {
        return PlatformUI.getWorkbench();
    }

    @Override
	protected void initializeDefaultPreferences(IPreferenceStore store) {
    }

    public static void log(String message) {
        getDefault().getLog().log(
                StatusUtil.newStatus(IStatus.ERROR, message, null));    
    }
    
    public static void log(Throwable t) {
		getDefault().getLog().log(getStatus(t));
	}

	public static IStatus getStatus(Throwable t) {
		String message = StatusUtil.getLocalizedMessage(t);

		return newError(message, t);
	}

	public static IStatus newError(String message, Throwable t) {
		String pluginId = "org.eclipse.ui.workbench"; //$NON-NLS-1$
		int errorCode = IStatus.OK;

		if (t instanceof CoreException) {
			CoreException ce = (CoreException) t;
			pluginId = ce.getStatus().getPlugin();
			errorCode = ce.getStatus().getCode();
		}

		return new Status(IStatus.ERROR, pluginId, errorCode, message,
				StatusUtil.getCause(t));
	}
    
    public static void log(String message, Throwable t) {
        IStatus status = StatusUtil.newStatus(IStatus.ERROR, message, t);
        log(message, status);
    }
    
    public static void log(Class clazz, String methodName, Throwable t) {
        String msg = MessageFormat.format("Exception in {0}.{1}: {2}", //$NON-NLS-1$
                new Object[] { clazz.getName(), methodName, t });
        log(msg, t);
    }
    
    public static void log(String message, IStatus status) {


        if (message != null) {
            getDefault().getLog().log(
                    StatusUtil.newStatus(IStatus.ERROR, message, null));
        }

        getDefault().getLog().log(status);
    }

    public static void log(IStatus status) {
        getDefault().getLog().log(status);
    }
    
    public DecoratorManager getDecoratorManager() {
		return (DecoratorManager) e4Context.get(IDecoratorManager.class);
    }

    @Override
	public void start(BundleContext context) throws Exception {
    	context.addBundleListener(getBundleListener());
        super.start(context);
        bundleContext = context;
        
        JFaceUtil.initializeJFace();
		
		parseBidiArguments();
		Window.setDefaultOrientation(getDefaultOrientation());

        Bundle uiBundle = Platform.getBundle(PlatformUI.PLUGIN_ID);
        try {
        	if(uiBundle != null)
        		uiBundle.start(Bundle.START_TRANSIENT);
        } catch (BundleException e) {
            WorkbenchPlugin.log("Unable to load UI activator", e); //$NON-NLS-1$
        }

    }

	private void parseBidiArguments() {
		String[] commandLineArgs = Platform.getCommandLineArgs();
		String bidiParams = null;
		for (int i = 0; i < commandLineArgs.length - 1; i++) {
			if (commandLineArgs[i].equals(BIDI_COMMAND_LINE)) {
				bidiParams = commandLineArgs[i + 1];
			}
		}
		if (bidiParams != null) {
			String[] bidiProps = Util.getArrayFromList(bidiParams, ";"); //$NON-NLS-1$
			for (int i = 0; i < bidiProps.length; ++i) {
				int eqPos = bidiProps[i].indexOf("="); //$NON-NLS-1$
				if ((eqPos > 0) && (eqPos < bidiProps[i].length() - 1)) {
					String nameProp = bidiProps[i].substring(0, eqPos);
					String valProp = bidiProps[i].substring(eqPos + 1);
					if (nameProp.equals(BIDI_SUPPORT_OPTION)) {
						BidiUtils.setBidiSupport("y".equals(valProp)); //$NON-NLS-1$
					} else if (nameProp.equalsIgnoreCase(BIDI_TEXTDIR_OPTION)) {
						try {
							BidiUtils.setTextDirection(valProp.intern());
						} catch (IllegalArgumentException e) {
							WorkbenchPlugin.log(e);
						}
					}
				}
			}
		}
	}

    private int getDefaultOrientation() {
		
		String[] commandLineArgs = Platform.getCommandLineArgs();
		
		int orientation = getCommandLineOrientation(commandLineArgs);
		
		if(orientation != SWT.NONE) {
			return orientation;
		}
		
		orientation = getSystemPropertyOrientation();
		
		if(orientation != SWT.NONE) {
			return orientation;
		}

		return checkCommandLineLocale(); //Use the default value if there is nothing specified
	}

	private Boolean isBidiMessageText() {
		String message = WorkbenchMessages.Startup_Loading_Workbench;
		if (message == null)
			return null;

		try {
			boolean isBidi = com.ibm.icu.text.Bidi.requiresBidi(message.toCharArray(), 0,
					message.length());
			return new Boolean(isBidi);
		} catch (NoClassDefFoundError e) {
			return null;
		}
	}


	private int checkCommandLineLocale() {
		if (System.getProperty(NL_USER_PROPERTY) == null) {
			Boolean needRTL = isBidiMessageText();
			if (needRTL != null && needRTL.booleanValue())
				return SWT.RIGHT_TO_LEFT;
		} else {
			String lang = Locale.getDefault().getLanguage();
			boolean bidiLangauage = "iw".equals(lang) || "he".equals(lang) || "ar".equals(lang) || //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					"fa".equals(lang) || "ur".equals(lang); //$NON-NLS-1$ //$NON-NLS-2$
			if (bidiLangauage) {
				Boolean needRTL = isBidiMessageText();
				if (needRTL == null)
					return SWT.RIGHT_TO_LEFT;
				if (needRTL.booleanValue())
					return SWT.RIGHT_TO_LEFT;
			}
		}
		return SWT.NONE;
	}

	private int getSystemPropertyOrientation() {
		String orientation = System.getProperty(ORIENTATION_PROPERTY);
		if(RIGHT_TO_LEFT.equals(orientation)) {
			return SWT.RIGHT_TO_LEFT;
		}
		if(LEFT_TO_RIGHT.equals(orientation)) {
			return SWT.LEFT_TO_RIGHT;
		}
		return SWT.NONE;
	}

	private int getCommandLineOrientation(String[] commandLineArgs) {
		for (int i = 0; i < commandLineArgs.length - 1; i++) {
			if(commandLineArgs[i].equalsIgnoreCase(ORIENTATION_COMMAND_LINE)){
				String orientation = commandLineArgs[i+1];
				if(orientation.equals(RIGHT_TO_LEFT)){
					System.setProperty(ORIENTATION_PROPERTY,RIGHT_TO_LEFT);
					return SWT.RIGHT_TO_LEFT;
				}
				if(orientation.equals(LEFT_TO_RIGHT)){
					System.setProperty(ORIENTATION_PROPERTY,LEFT_TO_RIGHT);
					return SWT.LEFT_TO_RIGHT;
				}
			}
		}
		
		return SWT.NONE;
	}

    public Bundle[] getBundles() {
        return bundleContext == null ? new Bundle[0] : bundleContext
                .getBundles();
    }
    
    public BundleContext getBundleContext() {
    	return bundleContext;
    }

    public String getAppName() {
        return getProductInfo().getAppName();
    }

    public String getProductName() {
        return getProductInfo().getProductName();
    }

    public ImageDescriptor[] getWindowImages() {
        return getProductInfo().getWindowImages();
    }

    private ProductInfo getProductInfo() {
        if (productInfo == null) {
			productInfo = new ProductInfo(Platform.getProduct());
		}
        return productInfo;
    }

    @Override
	public void stop(BundleContext context) throws Exception {
    	if (bundleListener!=null) {
    		context.removeBundleListener(bundleListener);
    		bundleListener = null;
    	}
		if (debugTracker != null) {
			debugTracker.close();
			debugTracker = null;
		}
		if (testableTracker != null) {
			testableTracker.close();
			testableTracker = null;
		}
        super.stop(context);     
    } 
    
    public IWizardRegistry getNewWizardRegistry() {
		return e4Context.get(NewWizardRegistry.class);
    }
    
    public IWizardRegistry getImportWizardRegistry() {
		return e4Context.get(ImportWizardRegistry.class);
    }
    
    public IWizardRegistry getExportWizardRegistry() {
		return e4Context.get(ExportWizardRegistry.class);
    }
    
    public IPath getDataLocation() {
        try {
            return getStateLocation();
        } catch (IllegalStateException e) {
            return null;
        }
    }

		bundleContext.addBundleListener(bundleListener);
	}    

		bundleContext.removeBundleListener(bundleListener);
	}    
	
		return bundleContext.getBundles().length;
	}
	
		ServiceReference[] ref;
		try {
			ref = bundleContext.getServiceReferences(OutputStream.class.getName(), null);
		} catch (InvalidSyntaxException e) {
			return null;
		}
		if(ref==null) {
			return null;
		}
		for (int i = 0; i < ref.length; i++) {
			String name = (String) ref[i].getProperty("name"); //$NON-NLS-1$
			if (name != null && name.equals("splashstream")) {  //$NON-NLS-1$
				Object result = bundleContext.getService(ref[i]);
				bundleContext.ungetService(ref[i]);
				return (OutputStream) result;
			}
		}
		return null;
	}

	private BundleListener getBundleListener() {
		if (bundleListener == null) {
			bundleListener = new SynchronousBundleListener() {
				@Override
				public void bundleChanged(BundleEvent event) {
					WorkbenchPlugin.this.bundleChanged(event);
				}
			};
		}
		return bundleListener;
	}

	private void bundleChanged(BundleEvent event) {
		int eventType = event.getType();
		synchronized (startingBundles) {
			switch (eventType) {
				case BundleEvent.STARTING :
					startingBundles.add(event.getBundle());
					break;
				case BundleEvent.STARTED :
				case BundleEvent.STOPPED :
					startingBundles.remove(event.getBundle());
					break;
				default :
					break;
			}
		}
	}

	public boolean isStarting(Bundle bundle) {
		synchronized (startingBundles) {
			return startingBundles.contains(bundle);
		}
	}

	public static boolean isSplashHandleSpecified() {
		return System.getProperty(PROP_SPLASH_HANDLE) != null;
	}
	
	public static Shell getSplashShell(Display display)
			throws NumberFormatException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		Shell splashShell = (Shell) display.getData(DATA_SPLASH_SHELL); 
		if (splashShell != null)
			return splashShell;
		
		String splashHandle = System.getProperty(PROP_SPLASH_HANDLE);
		if (splashHandle == null) {
			return null;
		}
	
		try {
			Method method = Shell.class.getMethod(
					"internal_new", new Class[] { Display.class, int.class }); //$NON-NLS-1$
			splashShell = (Shell) method.invoke(null, new Object[] { display,
					new Integer(splashHandle) });
		} catch (NoSuchMethodException e) {
			try {
				Method method = Shell.class
						.getMethod(
								"internal_new", new Class[] { Display.class, long.class }); //$NON-NLS-1$

				splashShell = (Shell) method.invoke(null, new Object[] {
						display, new Long(splashHandle) });
			} catch (NoSuchMethodException e2) {
			}
		}

		display.setData(DATA_SPLASH_SHELL, splashShell);
		return splashShell;
	}
	
	public static void unsetSplashShell(Display display) {
		Shell splashShell = (Shell) display.getData(DATA_SPLASH_SHELL);
		if (splashShell != null) {
			if (!splashShell.isDisposed())
				splashShell.dispose();
			display.setData(DATA_SPLASH_SHELL, null);
		}

	}

	public void initializeContext(IEclipseContext context) {
		e4Context = context;
		e4Context.set(IPerspectiveRegistry.class.getName(), new ContextFunction() {

			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				if (perspRegistry == null) {
					perspRegistry = ContextInjectionFactory.make(
							PerspectiveRegistry.class, e4Context);
				}
				return perspRegistry;
			}
		});
		e4Context.set(IViewRegistry.class.getName(), new ContextFunction() {

			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				if (viewRegistry == null) {
					viewRegistry = ContextInjectionFactory.make(ViewRegistry.class,
							e4Context);
				}
				return viewRegistry;
			}
		});
		e4Context.set(ActionSetRegistry.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				if (actionSetRegistry == null) {
					actionSetRegistry = new ActionSetRegistry();
				}
				return actionSetRegistry;
			}
		});
		context.set(IDecoratorManager.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				if (decoratorManager == null) {
					decoratorManager = new DecoratorManager();
				}
				return decoratorManager;
			}
		});
		context.set(ExportWizardRegistry.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				return ExportWizardRegistry.getInstance();
			}
		});
		context.set(ImportWizardRegistry.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				return ImportWizardRegistry.getInstance();
			}
		});
		context.set(IIntroRegistry.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				if (introRegistry == null) {
					introRegistry = new IntroRegistry();
				}
				return introRegistry;
			}
		});
		context.set(NewWizardRegistry.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				return NewWizardRegistry.getInstance();
			}
		});
		context.set(IWorkbenchOperationSupport.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				if (operationSupport == null) {
					operationSupport = new WorkbenchOperationSupport();
				}
				return operationSupport;
			}
		});
		context.set(PreferenceManager.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				if (preferenceManager == null) {
					preferenceManager = new WorkbenchPreferenceManager(
							PREFERENCE_PAGE_CATEGORY_SEPARATOR);

					PreferencePageRegistryReader registryReader = new PreferencePageRegistryReader(
							getWorkbench());
					registryReader.loadFromRegistry(Platform.getExtensionRegistry());
					preferenceManager.addPages(registryReader.getTopLevelNodes());

				}
				return preferenceManager;
			}
		});
		context.set(ISharedImages.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				if (sharedImages == null) {
					sharedImages = new SharedImages();
				}
				return sharedImages;
			}
		});

		context.set(IThemeRegistry.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				if (themeRegistry == null) {
					themeRegistry = new ThemeRegistry();
					ThemeRegistryReader reader = new ThemeRegistryReader();
					reader.readThemes(Platform.getExtensionRegistry(), themeRegistry);
				}
				return themeRegistry;
			}
		});
		context.set(IWorkingSetManager.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				if (workingSetManager == null) {
					workingSetManager = new WorkingSetManager(bundleContext);
					workingSetManager.restoreState();
				}
				return workingSetManager;
			}
		});
		context.set(WorkingSetRegistry.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				if (workingSetRegistry == null) {
					workingSetRegistry = new WorkingSetRegistry();
					workingSetRegistry.load();
				}
				return workingSetRegistry;
			}
		});
		context.set(IEditorRegistry.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				if (editorRegistry == null) {
					editorRegistry = new EditorRegistry();
				}
				return editorRegistry;
			}
		});
		context.set(IHelpService.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				if (helpService == null) {
					helpService = new HelpServiceImpl();
				}
				return helpService;
			}
		});
		context.set(ICommandHelpService.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				if (commandHelpService == null) {
					commandHelpService = ContextInjectionFactory.make(CommandHelpServiceImpl.class,
							e4Context);
				}
				return commandHelpService;
			}
		});
	}

	public DebugOptions getDebugOptions() {
		if (bundleContext == null)
			return null;
		if (debugTracker == null) {
			debugTracker = new ServiceTracker(bundleContext, DebugOptions.class.getName(), null);
			debugTracker.open();
		}
		return (DebugOptions) debugTracker.getService();
	}
	

	public TestableObject getTestableObject() {
		if (bundleContext == null)
			return null;
		if (testableTracker == null) {
			testableTracker = new ServiceTracker(bundleContext, TestableObject.class.getName(), null);
			testableTracker.open();
		}
		return (TestableObject) testableTracker.getService();
	}
}
