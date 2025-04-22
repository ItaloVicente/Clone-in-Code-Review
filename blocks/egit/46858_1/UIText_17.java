package org.eclipse.egit.gitflow.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.statushandlers.StatusManager;
import org.osgi.framework.BundleContext;

public class Activator extends AbstractUIPlugin {

	private static Activator plugin;

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

	public static Activator getDefault() {
		return plugin;
	}

	public static IStatus error(String message, Throwable throwable) {
		return new Status(IStatus.ERROR, getPluginId(), 0, message, throwable);
	}

	public static IStatus error(String message) {
		return new Status(IStatus.ERROR, getPluginId(), message);
	}

	public static String getPluginId() {
		return getDefault().getBundle().getSymbolicName();
	}
	
	public static void handleError(String message, Throwable throwable,
			boolean show) {
		handleIssue(IStatus.ERROR, message, throwable, show);
	}

	public static void handleIssue(int severity, String message, Throwable throwable,
			boolean show) {
		IStatus status = new Status(severity, getPluginId(), message,
				throwable);
		int style = StatusManager.LOG;
		if (show)
			style |= StatusManager.SHOW;
		StatusManager.getManager().handle(status, style);
	}
}
