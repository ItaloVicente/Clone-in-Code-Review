package org.eclipse.ui.tests.browser.internal;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.browser.BrowserDescriptorDialog;

import junit.framework.TestCase;

public class DialogsTestCase extends TestCase {
	private Shell getShell() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	}

	public void testExternalBrowserDialog() {
		BrowserDescriptorDialog bdd = new BrowserDescriptorDialog(getShell());
		UITestHelper.assertDialog(bdd);
	}
}
