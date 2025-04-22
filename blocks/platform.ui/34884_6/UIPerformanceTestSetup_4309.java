package org.eclipse.ui.tests.performance;

import org.eclipse.ui.plugin.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.BundleContext;

public class UIPerformancePlugin extends AbstractUIPlugin {

	private static UIPerformancePlugin plugin;
	
	private BundleContext context;

	public UIPerformancePlugin() {
		plugin = this;
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		this.context = context;
	}

	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
		context = null;
	}

	public static UIPerformancePlugin getDefault() {
		return plugin;
	}

	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.ui.tests.performance", path);
	}

	public BundleContext getContext() {
		return context;
	}
}
