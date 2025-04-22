package org.eclipse.ui.tests.rcp;

import junit.framework.Assert;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.tests.harness.util.RCPTestWorkbenchAdvisor;
import org.eclipse.ui.tests.rcp.util.WorkbenchAdvisorObserver;

public class PlatformUITest extends TestCase {
	public static TestSuite suite() {
		TestSuite suite = new TestSuite("org.eclipse.ui.tests.rcp.PlatformUITest");
		suite.addTest(new PlatformUITest("testEarlyGetWorkbench"));
		suite.addTest(new PlatformUITest("testCreateDisplay"));
		suite.addTest(new PlatformUITest("testCreateAndRunWorkbench"));
		suite.addTest(new PlatformUITest("testCreateAndRunWorkbenchWithExceptionOnStartup"));
		return suite;
	}

    public PlatformUITest(String testName) {
        super(testName);
    }

    public void testEarlyGetWorkbench() {
        assertFalse(PlatformUI.isWorkbenchRunning());
        try {
            PlatformUI.getWorkbench();
            fail("Exception should have been thrown."); //$NON-NLS-1$
        } catch (IllegalStateException e) {
        }
    }

    public void testCreateDisplay() {
        Display disp = PlatformUI.createDisplay();
        assertNotNull(disp);
        assertFalse(disp.isDisposed());
        disp.dispose();
        assertTrue(disp.isDisposed());
    }

    public void testCreateAndRunWorkbench() {
        final Display display = PlatformUI.createDisplay();
        assertNotNull(display);

        CheckForWorkbench wa = new CheckForWorkbench(2);

        int code = PlatformUI.createAndRunWorkbench(display, wa);
        assertEquals(PlatformUI.RETURN_OK, code);
        assertTrue(wa.checkComplete);
        display.dispose();
        assertTrue(display.isDisposed());

		assertEquals("Async run during startup.  See RCPTestWorkbenchAdvisor.preStartup()",
				Boolean.FALSE,
				RCPTestWorkbenchAdvisor.asyncDuringStartup);

		assertEquals(
				"Sync from qualified thread did not run during startup.  See RCPTestWorkbenchAdvisor.preStartup()",
				Boolean.TRUE,
				RCPTestWorkbenchAdvisor.syncWithDisplayAccess);
    	assertEquals(
				"Async from qualified thread did not run during startup.  See RCPTestWorkbenchAdvisor.preStartup()",
				Boolean.TRUE,
				RCPTestWorkbenchAdvisor.asyncWithDisplayAccess);
		assertEquals(
				"Sync from un-qualified thread ran during startup.  See RCPTestWorkbenchAdvisor.preStartup()",
				Boolean.FALSE,
				RCPTestWorkbenchAdvisor.syncWithoutDisplayAccess);
       	assertEquals(
				"Async from un-qualified thread ran during startup.  See RCPTestWorkbenchAdvisor.preStartup()",
				Boolean.FALSE,
				RCPTestWorkbenchAdvisor.asyncWithoutDisplayAccess);
       	
		assertFalse(
				"DisplayAccess.accessDisplayDuringStartup() in UI thread did not result in exception.",
				RCPTestWorkbenchAdvisor.displayAccessInUIThreadAllowed);
    }
    
    public void testCreateAndRunWorkbenchWithExceptionOnStartup() {
        final Display display = PlatformUI.createDisplay();
        assertNotNull(display);

        WorkbenchAdvisorObserver wa = new WorkbenchAdvisorObserver(1) {
            public void preStartup() {
                throw new IllegalArgumentException("Thrown deliberately by PlatformUITest");
            }
        };

        int code = PlatformUI.createAndRunWorkbench(display, wa);
        assertEquals(PlatformUI.RETURN_UNSTARTABLE, code);
        assertFalse(PlatformUI.isWorkbenchRunning());
        display.dispose();
        assertTrue(display.isDisposed());
	}
}

class CheckForWorkbench extends WorkbenchAdvisorObserver {

    public boolean checkComplete = false;

    public CheckForWorkbench(int idleBeforeExit) {
        super(idleBeforeExit);
    }

    public void eventLoopIdle(Display display) {
        super.eventLoopIdle(display);

        if (checkComplete)
            return;

        Assert.assertNotNull(PlatformUI.getWorkbench());
        checkComplete = true;
    }
}
