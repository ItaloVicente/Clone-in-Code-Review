
package org.eclipse.ui.examples.contributions;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class Activator extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "org.eclipse.ui.examples.contributions"; //$NON-NLS-1$

	private static Activator plugin;

	public Activator() {
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		DEBUG_COMMANDS = getDebugOption("/trace/commands"); //$NON-NLS-1$
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public static Activator getDefault() {
		return plugin;
	}

	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	public static boolean DEBUG_COMMANDS = false;
	
	private static boolean getDebugOption(String option) {
        return "true".equalsIgnoreCase(Platform.getDebugOption(PlatformUI.PLUGIN_ID + option)); //$NON-NLS-1$
    }
}
