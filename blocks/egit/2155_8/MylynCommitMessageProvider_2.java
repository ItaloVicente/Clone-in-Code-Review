package org.eclipse.egilt.mylyn;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

public class EgitMylynPlugin extends Plugin{
	private static EgitMylynPlugin plugin;

	public static EgitMylynPlugin getDefault() {
		if (plugin == null){
			plugin = new EgitMylynPlugin();
		}
		return plugin;
	}
	
	public static String getPluginId() {
		return getDefault().getBundle().getSymbolicName();
	}
	
	public void start(BundleContext bundleContext) throws Exception {
		super.start(bundleContext);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		super.stop(bundleContext);
	}

	public static void logError(final String message, final Throwable thr) {
		getDefault().getLog().log(
				new Status(IStatus.ERROR, "egit.mylyn", 0, message, thr)); //$NON-NLS-1$
	}
}
