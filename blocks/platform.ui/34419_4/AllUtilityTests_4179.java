package org.eclipse.ui.tests.forms.plugin;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class FormsTestPlugin extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "org.eclipse.ui.tests.forms";

	private static FormsTestPlugin plugin;
	
	public FormsTestPlugin() {
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public static FormsTestPlugin getDefault() {
		return plugin;
	}

}
