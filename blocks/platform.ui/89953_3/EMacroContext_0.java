package org.eclipse.e4.core.macros;

import java.io.File;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.e4.core.macros.internal.MacroManager;
import org.osgi.framework.BundleContext;

public class Activator extends Plugin {

	private static Activator plugin;

	public Activator() {
		super();
		plugin = this;
	}

	public static Activator getDefault() {
		return plugin;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);

		try {
			IPath stateLocation = this.getStateLocation();
			stateLocation.append("macros"); //$NON-NLS-1$
			File userHome = new File(System.getProperty("user.home")); //$NON-NLS-1$
			File eclipseUserHome = new File(userHome, ".eclipse"); //$NON-NLS-1$
			File eclipseUserHomeMacros = new File(eclipseUserHome, "org.eclipse.e4.core.macros"); //$NON-NLS-1$
			File eclipseUserHomeMacrosLoadDir = new File(eclipseUserHomeMacros, "macros"); //$NON-NLS-1$
			if (!eclipseUserHomeMacrosLoadDir.exists()) {
				eclipseUserHomeMacrosLoadDir.mkdirs();
			}
			MacroManager.createDefaultInstance(stateLocation.toFile(), eclipseUserHomeMacrosLoadDir);
		} catch (Exception e) {
			log(e);
		}
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		MacroManager.disposeDefaultInstance();
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
