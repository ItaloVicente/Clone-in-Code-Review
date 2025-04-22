package org.eclipse.ui.tests.dynamicplugins;

import org.eclipse.ui.internal.browser.WorkbenchBrowserSupport;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;

public class BrowserTests extends DynamicTestCase {

    public BrowserTests(String testName) {
        super(testName);
    }

    public void testBrowserSupport() {
        WorkbenchBrowserSupport support = (WorkbenchBrowserSupport) WorkbenchBrowserSupport.getInstance();
        try {
            support.setDesiredBrowserSupportId(getExtensionId());
            assertFalse(support.hasNonDefaultBrowser());

            getBundle();
            support.setDesiredBrowserSupportId(getExtensionId());
            assertTrue(support.hasNonDefaultBrowser());

            removeBundle();
            support.setDesiredBrowserSupportId(getExtensionId());
            assertFalse(support.hasNonDefaultBrowser());
        }
        finally {
            support.setDesiredBrowserSupportId(null);
        }
    }

    @Override
	protected String getExtensionId() {
        return "newBrowser1.testDynamicBrowserAddition";
    }

    @Override
	protected String getExtensionPoint() {
        return IWorkbenchRegistryConstants.PL_BROWSER_SUPPORT;
    }

    @Override
	protected String getInstallLocation() {
        return "data/org.eclipse.newBrowser1";
    }

    @Override
	protected String getMarkerClass() {
        return "org.eclipse.ui.dynamic.DynamicBrowserSupport";
    }

}
