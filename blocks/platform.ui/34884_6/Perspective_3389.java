package org.eclipse.ui.examples.rcp.mail;

import org.eclipse.ui.plugin.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.BundleContext;

public class Part3Plugin extends AbstractUIPlugin {

	private static Part3Plugin plugin;
	
	public Part3Plugin() {
		plugin = this;
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	public static Part3Plugin getDefault() {
		return plugin;
	}

	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.ui.tutorials.rcp.part3", path);
	}
}
