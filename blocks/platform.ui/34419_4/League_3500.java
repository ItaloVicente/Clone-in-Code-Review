package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague;

import org.eclipse.emf.common.EMFPlugin;

import org.eclipse.emf.common.util.ResourceLocator;

public final class HockeyleaguePlugin extends EMFPlugin {
	public static final HockeyleaguePlugin INSTANCE = new HockeyleaguePlugin();

	private static Implementation plugin;

	public HockeyleaguePlugin() {
		super
		  (new ResourceLocator [] {
		   });
	}

	public ResourceLocator getPluginResourceLocator() {
		return plugin;
	}

	public static Implementation getPlugin() {
		return plugin;
	}

	public static class Implementation extends EclipsePlugin {
		public Implementation() {
			super();

			plugin = this;
		}
	}

}
