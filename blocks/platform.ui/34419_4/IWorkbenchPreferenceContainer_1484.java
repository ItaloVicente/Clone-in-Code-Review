package org.eclipse.ui.plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IPluginDescriptor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WWinPluginAction;
import org.eclipse.ui.internal.util.BundleUtility;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;

public abstract class AbstractUIPlugin extends Plugin {

    private static final String FN_DIALOG_SETTINGS = "dialog_settings.xml"; //$NON-NLS-1$

    private IDialogSettings dialogSettings = null;

    private ScopedPreferenceStore preferenceStore;

    private ImageRegistry imageRegistry = null;

    private BundleListener bundleListener;
    
    @Deprecated
	public AbstractUIPlugin(IPluginDescriptor descriptor) {
        super(descriptor);
    }

    public AbstractUIPlugin() {
        super();
    }

    protected ImageRegistry createImageRegistry() {
    	
    	if(Display.getCurrent() != null) {
			return new ImageRegistry(Display.getCurrent());
		}
    	
    	if(PlatformUI.isWorkbenchRunning()) {
			return new ImageRegistry(PlatformUI.getWorkbench().getDisplay());
		}
    	
    	throw new SWTError(SWT.ERROR_THREAD_INVALID_ACCESS);
    }
    
    public IDialogSettings getDialogSettings() {
        if (dialogSettings == null) {
			loadDialogSettings();
		}
        return dialogSettings;
    }

    public ImageRegistry getImageRegistry() {
        if (imageRegistry == null) {
            imageRegistry = createImageRegistry();
            initializeImageRegistry(imageRegistry);
        }
        return imageRegistry;
    }

    public IPreferenceStore getPreferenceStore() {
        if (preferenceStore == null) {
            preferenceStore = new ScopedPreferenceStore(new InstanceScope(),getBundle().getSymbolicName());

        }
        return preferenceStore;
    }

    public IWorkbench getWorkbench() {
        return PlatformUI.getWorkbench();
    }

    @Deprecated
	protected void initializeDefaultPreferences(IPreferenceStore store) {
    }

    @Deprecated
	@Override
	protected void initializeDefaultPluginPreferences() {

        loadPreferenceStore();
        initializeDefaultPreferences(getPreferenceStore());
    }

    protected void initializeImageRegistry(ImageRegistry reg) {
    }

    protected void loadDialogSettings() {
        dialogSettings = new DialogSettings("Workbench"); //$NON-NLS-1$

        IPath dataLocation = getStateLocationOrNull();
        if (dataLocation != null) {
	        String readWritePath = dataLocation.append(FN_DIALOG_SETTINGS)
	                .toOSString();
	        File settingsFile = new File(readWritePath);
	        if (settingsFile.exists()) {
	            try {
	                dialogSettings.load(readWritePath);
	            } catch (IOException e) {
	                dialogSettings = new DialogSettings("Workbench"); //$NON-NLS-1$
	            }
	            
	            return;
	        }
        }

        URL dsURL = BundleUtility.find(getBundle(), FN_DIALOG_SETTINGS);
        if (dsURL == null) {
			return;
		}

        InputStream is = null;
        try {
            is = dsURL.openStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, "utf-8")); //$NON-NLS-1$
            dialogSettings.load(reader);
        } catch (IOException e) {
            dialogSettings = new DialogSettings("Workbench"); //$NON-NLS-1$
        } finally {
            try {
                if (is != null) {
					is.close();
				}
            } catch (IOException e) {
            }
        }
    }

    @Deprecated
	protected void loadPreferenceStore() {
    }

    protected void refreshPluginActions() {
        if (!PlatformUI.isWorkbenchRunning()) {
			return;
		}

        Display.getDefault().asyncExec(new Runnable() {
            @Override
			public void run() {
                WWinPluginAction.refreshActionList();
            }
        });
    }

    protected void saveDialogSettings() {
        if (dialogSettings == null) {
            return;
        }

        try {
        	IPath path = getStateLocationOrNull();
        	if(path == null) {
				return;
			}
            String readWritePath = path
                    .append(FN_DIALOG_SETTINGS).toOSString();
            dialogSettings.save(readWritePath);
        } catch (IOException e) {
        } catch (IllegalStateException e) {
        }
    }

    @Deprecated
	protected void savePreferenceStore() {
        savePluginPreferences();
    }

    @Deprecated
	@Override
	public void startup() throws CoreException {
        super.startup();
    }

    @Deprecated
	@Override
	public void shutdown() throws CoreException {
        super.shutdown();
    }

    @Override
	public void start(BundleContext context) throws Exception {
        super.start(context);
		final BundleContext fc = context;
        bundleListener = new BundleListener() {
            @Override
			public void bundleChanged(BundleEvent event) {
                if (event.getBundle() == getBundle()) {
                    if (event.getType() == BundleEvent.STARTED) {
                        if (getBundle().getState() == Bundle.ACTIVE) {
                            refreshPluginActions();
                        }
                        fc.removeBundleListener(this);
                    }
                }
            }
        };
        context.addBundleListener(bundleListener);
    }

    @Override
	public void stop(BundleContext context) throws Exception {
        try {
            if (bundleListener != null) {
                context.removeBundleListener(bundleListener);
            }
            saveDialogSettings();
            savePreferenceStore();
            preferenceStore = null;
            if (imageRegistry != null)
            	imageRegistry.dispose();
            imageRegistry = null;
        } finally {
            super.stop(context);
        }
    }

    public static ImageDescriptor imageDescriptorFromPlugin(String pluginId,
            String imageFilePath) {
        if (pluginId == null || imageFilePath == null) {
            throw new IllegalArgumentException();
        }

		IWorkbench workbench = PlatformUI.isWorkbenchRunning() ? PlatformUI.getWorkbench() : null;
		ImageDescriptor imageDescriptor = workbench == null ? null : workbench
				.getSharedImages().getImageDescriptor(imageFilePath);
		if (imageDescriptor != null)
			return imageDescriptor; // found in the shared images

        Bundle bundle = Platform.getBundle(pluginId);
        if (!BundleUtility.isReady(bundle)) {
			return null;
		}

        URL fullPathString = BundleUtility.find(bundle, imageFilePath);
        if (fullPathString == null) {
            try {
                fullPathString = new URL(imageFilePath);
            } catch (MalformedURLException e) {
                return null;
            }
			URL platformURL = FileLocator.find(fullPathString);
			if (platformURL != null) {
				fullPathString = platformURL;
			}
        }

        return ImageDescriptor.createFromURL(fullPathString);
    }
    
    private IPath getStateLocationOrNull() {
        try {
            return getStateLocation();
        } catch (IllegalStateException e) {
            return null;
        }
    }    
    
}
