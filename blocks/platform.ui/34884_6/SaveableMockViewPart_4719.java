package org.eclipse.ui.tests.api;

import junit.framework.TestCase;

import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

public class PlatformUITest extends TestCase {

    public PlatformUITest(String testName) {
        super(testName);
    }

    public void testGetWorkbench() throws Throwable {
        IWorkbench wb = PlatformUI.getWorkbench();
        assertNotNull(wb);
    }

    public void testPLUGIN_ID() {
        assertNotNull(PlatformUI.PLUGIN_ID);
    }
}
