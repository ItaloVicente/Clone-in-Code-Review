package org.eclipse.ui.internal.views;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public final class ViewsPlugin extends AbstractUIPlugin {
    public static final String PLUGIN_ID = "org.eclipse.ui.views"; //$NON-NLS-1$
	
	private final static String ICONS_PATH = "$nl$/icons/full/";//$NON-NLS-1$

    private static ViewsPlugin instance;

    public static ViewsPlugin getDefault() {
        return instance;
    }

    public ViewsPlugin() {
        super();
        instance = this;
    }
	
	public static ImageDescriptor getViewImageDescriptor(String relativePath){
		return imageDescriptorFromPlugin(PLUGIN_ID, ICONS_PATH + relativePath);
	}
    
    public static Object getAdapter(Object sourceObject, Class adapter, boolean activatePlugins) {
    	Assert.isNotNull(adapter);
        if (sourceObject == null) {
            return null;
        }
        if (adapter.isInstance(sourceObject)) {
            return sourceObject;
        }

        if (sourceObject instanceof IAdaptable) {
            IAdaptable adaptable = (IAdaptable) sourceObject;

            Object result = adaptable.getAdapter(adapter);
            if (result != null) {
                Assert.isTrue(adapter.isInstance(result));
                return result;
            }
        } 
        
        if (!(sourceObject instanceof PlatformObject)) {
        	Object result;
        	if (activatePlugins) {
        		result = Platform.getAdapterManager().loadAdapter(sourceObject, adapter.getName());
        	} else {
        		result = Platform.getAdapterManager().getAdapter(sourceObject, adapter);
        	}
            if (result != null) {
                return result;
            }
        }

        return null;
    }
}
