package org.eclipse.e4.ui.macros;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

@SuppressWarnings("javadoc")
public class Activator extends AbstractUIPlugin {

	private static Activator plugin;

	public Activator() {
		super();
		plugin = this;
	}

	public static Activator getDefault() {
		return plugin;
	}

	public static ImageDescriptor getImageDescriptor(String key) {
		return getDefault().getImageRegistry().getDescriptor(key);
	}

}
