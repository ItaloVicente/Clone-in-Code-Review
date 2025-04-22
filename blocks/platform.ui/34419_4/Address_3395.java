package org.eclipse.ui.internal.examples.navigator;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class Activator extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "org.eclipse.ui.examples.navigator"; //$NON-NLS-1$
	  
	private static Activator plugin;  
	public Activator() {
		plugin = this;
	}

	public void start(BundleContext context) throws Exception {
		super.start(context); 
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);  
	}

	public static Activator getDefault() {
		return plugin;
	} 

	public static void logError(int aCode, String aMessage,
			Throwable anException) {
		getDefault().getLog().log(
				createErrorStatus(aCode, aMessage, anException));
	}

	public static void log(int severity, int aCode, String aMessage,
			Throwable exception) {
		log(createStatus(severity, aCode, aMessage, exception));
	}

	public static void log(IStatus aStatus) {
		getDefault().getLog().log(aStatus);
	}

	public static IStatus createStatus(int severity, int aCode,
			String aMessage, Throwable exception) {
		return new Status(severity, PLUGIN_ID, aCode,
				aMessage != null ? aMessage : "No message.", exception); //$NON-NLS-1$
	}

	public static IStatus createErrorStatus(int aCode, String aMessage,
			Throwable exception) {
		return createStatus(IStatus.ERROR, aCode, aMessage, exception);
	}
}
