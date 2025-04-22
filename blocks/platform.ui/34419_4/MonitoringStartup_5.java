package org.eclipse.ui.internal.monitoring;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.monitoring.PreferenceConstants;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class MonitoringPlugin extends AbstractUIPlugin {
	private static MonitoringPlugin plugin;

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public static MonitoringPlugin getDefault() {
		return plugin;
	}

	public static void logError(String message, Throwable e) {
		log(new Status(IStatus.ERROR, PreferenceConstants.PLUGIN_ID, message, e));
	}

	public static void logWarning(String message) {
		log(new Status(IStatus.WARNING, PreferenceConstants.PLUGIN_ID, message));
	}

	private static void log(IStatus status) {
		plugin.getLog().log(status);
	}
}
