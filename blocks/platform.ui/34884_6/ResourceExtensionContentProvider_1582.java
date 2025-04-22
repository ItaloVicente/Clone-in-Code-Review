package org.eclipse.ui.internal.navigator.resources.plugin;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class WorkbenchNavigatorPlugin extends AbstractUIPlugin {
	private static WorkbenchNavigatorPlugin plugin;

	public static String PLUGIN_ID = "org.eclipse.ui.navigator.resources"; //$NON-NLS-1$

	public WorkbenchNavigatorPlugin() {
		super();
		plugin = this;
	}

	public static WorkbenchNavigatorPlugin getDefault() {
		return plugin;
	}

	public static IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	public static void log(String message, IStatus status) {
		if (message != null) {
			getDefault().getLog().log(
					new Status(IStatus.ERROR, PLUGIN_ID, 0, message, null));
			System.err.println(message + "\nReason:"); //$NON-NLS-1$
		}
		if(status != null) {
			getDefault().getLog().log(status);
			System.err.println(status.getMessage());
		}
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
	

	public static IStatus createErrorStatus(String aMessage,	Throwable exception) {
		return createStatus(IStatus.ERROR, 0, aMessage, exception);
	}
	
	public static IStatus createErrorStatus(String aMessage) {
		return createStatus(IStatus.ERROR, 0, aMessage, null);
	}
	
	
	public static IStatus createInfoStatus(String aMessage) {
		return createStatus(IStatus.INFO, 0, aMessage, null);
	}
	
	
	public static IStatus createWarningStatus(String aMessage) {
		return createStatus(IStatus.WARNING, 0, aMessage, null);
	}
}
