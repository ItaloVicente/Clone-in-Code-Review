package org.eclipse.e4.core.macros;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;

public class Activator extends Plugin {

	private static Activator plugin;

	public Activator() {
		super();
		plugin = this;
	}

	public static Activator getDefault() {
		return plugin;
	}

	public static void log(Throwable exception) {
		try {
			if (plugin != null) {
				plugin.getLog().log(new Status(IStatus.ERROR, plugin.getBundle().getSymbolicName(),
						exception.getMessage(), exception));
			} else {
				exception.printStackTrace();
			}
		} catch (Exception e) {
			exception.printStackTrace();
		}
	}
}
