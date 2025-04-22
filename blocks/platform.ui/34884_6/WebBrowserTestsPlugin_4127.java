package org.eclipse.ui.tests.browser.internal;

import org.eclipse.ui.plugin.AbstractUIPlugin;
public class WebBrowserTestsPlugin extends AbstractUIPlugin {
	public static final String PLUGIN_ID = "org.eclipse.ui.browser.tests";

	private static WebBrowserTestsPlugin singleton;

	public WebBrowserTestsPlugin() {
		super();
		singleton = this;
	}

	public static WebBrowserTestsPlugin getInstance() {
		return singleton;
	}
}
