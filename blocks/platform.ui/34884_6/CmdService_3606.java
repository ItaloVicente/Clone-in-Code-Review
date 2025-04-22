package org.eclipse.e4.ui.menu.tests;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class Activator extends AbstractUIPlugin {

	private static Activator plugin;

	public static final String PLUGIN_ID = "org.eclipse.e4.ui.menu.tests"; //$NON-NLS-1$

	public static Activator getDefault() {
		return plugin;
	}

	public Activator() {
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

}
