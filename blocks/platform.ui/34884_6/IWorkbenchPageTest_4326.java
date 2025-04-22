package org.eclipse.ui.tests.rcp;

import junit.framework.TestCase;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.tests.rcp.util.WorkbenchAdvisorObserver;

public class ActionBarConfigurerTest extends TestCase {

    public ActionBarConfigurerTest(String name) {
        super(name);
    }

    private Display display = null;

    protected void setUp() throws Exception {
        super.setUp();

        assertNull(display);
        display = PlatformUI.createDisplay();
        assertNotNull(display);
    }

    protected void tearDown() throws Exception {
        assertNotNull(display);
        display.dispose();
        assertTrue(display.isDisposed());
        super.tearDown();
    }

    public void testDefaults() {
        WorkbenchAdvisor wa = new WorkbenchAdvisorObserver(1) {

            public void fillActionBars(IWorkbenchWindow window,
                    IActionBarConfigurer actionBarConfig, int flags) {
                super.fillActionBars(window, actionBarConfig, flags);

                assertNotNull(actionBarConfig.getMenuManager());
                assertNotNull(actionBarConfig.getStatusLineManager());
                assertNotNull(actionBarConfig.getCoolBarManager());
            }
        };

        int code = PlatformUI.createAndRunWorkbench(display, wa);
        assertEquals(PlatformUI.RETURN_OK, code);
    }
}
