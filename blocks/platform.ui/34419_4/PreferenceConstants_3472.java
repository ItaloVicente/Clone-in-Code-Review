package org.eclipse.ui.examples.undo;

import org.eclipse.ui.plugin.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.BundleContext;

public class UndoPlugin extends AbstractUIPlugin {

	private static UndoPlugin plugin;

	public UndoPlugin() {
		plugin = this;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	public static UndoPlugin getDefault() {
		return plugin;
	}

	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.ui.examples.undo2", path);
	}
}
