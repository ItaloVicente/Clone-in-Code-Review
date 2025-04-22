package org.eclipse.ui.examples.jobs;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.IAdapterManager;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.examples.jobs.views.ProgressExampleAdapterFactory;
import org.eclipse.ui.examples.jobs.views.SlowElement;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class ProgressExamplesPlugin extends AbstractUIPlugin {
	private static ProgressExamplesPlugin plugin;
	public static String ID = "org.eclipse.ui.examples.job"; //$NON-NLS-1$
	
	public ProgressExamplesPlugin() {
		super();
		plugin = this;
	}

	public static ProgressExamplesPlugin getDefault() {
		return plugin;
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		IAdapterManager m = Platform.getAdapterManager();
		IAdapterFactory f = new ProgressExampleAdapterFactory();
		m.registerAdapters(f, SlowElement.class);
	}
}
