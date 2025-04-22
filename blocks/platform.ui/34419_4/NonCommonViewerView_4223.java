package org.eclipse.ui.tests.navigator;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class NavigatorTestsPlugin extends AbstractUIPlugin {

	private static NavigatorTestsPlugin plugin;
	public static String PLUGIN_ID = "org.eclipse.ui.tests.navigator"; //$NON-NLS-1$

	public NavigatorTestsPlugin() {
 		super();
		plugin = this;
	}

	public static NavigatorTestsPlugin getDefault() {
		return plugin;
	}



	public static void log(String message) {
		getDefault().getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, 0, message, null));
		System.err.println(message);
	}

	public static void log(String message, IStatus status) {
		if (message != null) {
			getDefault().getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, 0, message, null));
			System.err.println(message + "\nReason:"); //$NON-NLS-1$
		}
		getDefault().getLog().log(status);
		System.err.println(status.getMessage());
	}


	public static void logError(int aCode, String aMessage, Throwable anException) {
		getDefault().getLog().log(createErrorStatus(aCode, aMessage, anException));
	}

	public static void log(int severity, int aCode, String aMessage, Throwable exception) {
		log(createStatus(severity, aCode, aMessage, exception));
	}

	public static void log(IStatus aStatus) {
		getDefault().getLog().log(aStatus);
	}

	public static IStatus createStatus(int severity, int aCode, String aMessage, Throwable exception) {
		return new Status(severity, PLUGIN_ID, aCode, aMessage, exception);
	}

	public static IStatus createErrorStatus(int aCode, String aMessage, Throwable exception) {
		return createStatus(IStatus.ERROR, aCode, aMessage, exception);
	}
}
