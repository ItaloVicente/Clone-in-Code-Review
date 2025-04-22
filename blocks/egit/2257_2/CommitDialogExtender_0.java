package org.eclipse.egit.internal.mylyn.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class EGitMylynUI extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "org.eclipse.egit.mylyn.ui"; //$NON-NLS-1$

	private static EGitMylynUI plugin;
	
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public static EGitMylynUI getDefault() {
		return plugin;
	}

}
