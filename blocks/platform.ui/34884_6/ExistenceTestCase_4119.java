package org.eclipse.ui.tests.browser.internal;

import org.eclipse.ui.internal.browser.WebBrowserUIPlugin;

import junit.framework.TestCase;

public class ExistenceTestCase extends TestCase {
	public void testPluginExists() {
		assertNotNull(WebBrowserUIPlugin.getInstance());
	}
}
