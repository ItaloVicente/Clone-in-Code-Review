package org.eclipse.ui.tests.browser.internal;

import java.net.URL;

import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.eclipse.ui.internal.browser.WebBrowserPreference;

import junit.framework.TestCase;

public class ExternalBrowserTestCase extends TestCase {
	public void testBrowser() throws Exception {
		WebBrowserPreference.setBrowserChoice(WebBrowserPreference.EXTERNAL);
		IWorkbenchBrowserSupport wbs = WebBrowserTestsPlugin.getInstance().getWorkbench().getBrowserSupport();
		IWebBrowser wb = wbs.createBrowser("test2");
		
		wb.openURL(new URL("http://www.ibm.com"));
		
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		}
		
		wb.openURL(new URL("http://www.eclipse.org"));
		
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		}
		
		wb.close();
		
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		}
	}
}
