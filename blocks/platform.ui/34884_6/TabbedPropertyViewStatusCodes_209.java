package org.eclipse.ui.internal.views.properties.tabbed;

import org.eclipse.ui.plugin.AbstractUIPlugin;

public class TabbedPropertyViewPlugin
	extends AbstractUIPlugin {

	private static TabbedPropertyViewPlugin plugin;

	public TabbedPropertyViewPlugin() {
		super();
		plugin = this;
	}

	public static TabbedPropertyViewPlugin getPlugin() {
		return plugin;
	}
}
