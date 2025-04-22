package org.eclipse.ui.tests.rcp;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.internal.progress.ProgressManagerUtil;
import org.eclipse.ui.tests.rcp.util.WorkbenchAdvisorObserver;

public class WorkbenchAdvisorTest extends TestCase {

      public WorkbenchAdvisorTest(String name) {
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

    public void testEarlyGetWorkbench() {
        WorkbenchAdvisor wa = new WorkbenchAdvisorObserver(1) {

            public void initialize(IWorkbenchConfigurer configurer) {
                super.initialize(configurer);
                assertNotNull(PlatformUI.getWorkbench());
            }
        };

        int code = PlatformUI.createAndRunWorkbench(display, wa);
        assertEquals(PlatformUI.RETURN_OK, code);
    }

    public void testTwoDisplays() {
        WorkbenchAdvisorObserver wa = new WorkbenchAdvisorObserver(1);

        int code = PlatformUI.createAndRunWorkbench(display, wa);
        assertEquals(PlatformUI.RETURN_OK, code);
        assertFalse(display.isDisposed());
        display.dispose();
        assertTrue(display.isDisposed());

        display = PlatformUI.createDisplay();
        assertNotNull(display);

        WorkbenchAdvisorObserver wa2 = new WorkbenchAdvisorObserver(1);

        int code2 = PlatformUI.createAndRunWorkbench(display, wa2);
        assertEquals(PlatformUI.RETURN_OK, code2);
    }

    public void testTrivialOpenClose() {
        WorkbenchAdvisorObserver wa = new WorkbenchAdvisorObserver(1) {

            private boolean windowOpenCalled = false;

            private boolean windowCloseCalled = false;

            public void initialize(IWorkbenchConfigurer c) {
                super.initialize(c);
                c.getWorkbench().addWindowListener(new IWindowListener() {

                    public void windowActivated(IWorkbenchWindow window) {
                    }

                    public void windowDeactivated(IWorkbenchWindow window) {
                    }

                    public void windowClosed(IWorkbenchWindow window) {
                        windowCloseCalled = true;
                    }

                    public void windowOpened(IWorkbenchWindow window) {
                        windowOpenCalled = true;
                    }
                });
            }

            public void preWindowOpen(IWorkbenchWindowConfigurer c) {
                assertFalse(windowOpenCalled);
                super.preWindowOpen(c);
            }

            public void postWindowOpen(IWorkbenchWindowConfigurer c) {
                assertTrue(windowOpenCalled);
                super.postWindowOpen(c);
            }

            public boolean preWindowShellClose(IWorkbenchWindowConfigurer c) {
                assertFalse(windowCloseCalled);
                return super.preWindowShellClose(c);
            }

            public void postWindowClose(IWorkbenchWindowConfigurer c) {
                super.postWindowClose(c);
            }
        };

        int code = PlatformUI.createAndRunWorkbench(display, wa);
        assertEquals(PlatformUI.RETURN_OK, code);

        wa.resetOperationIterator();
        wa.assertNextOperation(WorkbenchAdvisorObserver.INITIALIZE);
        wa.assertNextOperation(WorkbenchAdvisorObserver.PRE_STARTUP);
        wa.assertNextOperation(WorkbenchAdvisorObserver.PRE_WINDOW_OPEN);
        wa.assertNextOperation(WorkbenchAdvisorObserver.FILL_ACTION_BARS);
        wa.assertNextOperation(WorkbenchAdvisorObserver.POST_WINDOW_OPEN);
        wa.assertNextOperation(WorkbenchAdvisorObserver.POST_STARTUP);
        wa.assertNextOperation(WorkbenchAdvisorObserver.PRE_SHUTDOWN);
        wa.assertNextOperation(WorkbenchAdvisorObserver.POST_SHUTDOWN);
        wa.assertAllOperationsExamined();
    }

    public void testTrivialRestoreClose() {
        WorkbenchAdvisorObserver wa = new WorkbenchAdvisorObserver(1) {

            public void initialize(IWorkbenchConfigurer c) {
                super.initialize(c);
                c.setSaveAndRestore(true);
            }

            public void eventLoopIdle(Display d) {
                workbenchConfig.getWorkbench().restart();
            }
        };

        int code = PlatformUI.createAndRunWorkbench(display, wa);
        assertEquals(PlatformUI.RETURN_RESTART, code);
        assertFalse(display.isDisposed());
        display.dispose();
        assertTrue(display.isDisposed());

        display = PlatformUI.createDisplay();
        WorkbenchAdvisorObserver wa2 = new WorkbenchAdvisorObserver(1) {

            public void initialize(IWorkbenchConfigurer c) {
                super.initialize(c);
                c.setSaveAndRestore(true);
            }
        };

        int code2 = PlatformUI.createAndRunWorkbench(display, wa2);
        assertEquals(PlatformUI.RETURN_OK, code2);

        wa2.resetOperationIterator();
        wa2.assertNextOperation(WorkbenchAdvisorObserver.INITIALIZE);
        wa2.assertNextOperation(WorkbenchAdvisorObserver.PRE_STARTUP);
        wa2.assertNextOperation(WorkbenchAdvisorObserver.PRE_WINDOW_OPEN);
        wa2.assertNextOperation(WorkbenchAdvisorObserver.FILL_ACTION_BARS);
        wa2.assertNextOperation(WorkbenchAdvisorObserver.POST_WINDOW_RESTORE);
        wa2.assertNextOperation(WorkbenchAdvisorObserver.POST_WINDOW_OPEN);
        wa2.assertNextOperation(WorkbenchAdvisorObserver.POST_STARTUP);
        wa2.assertNextOperation(WorkbenchAdvisorObserver.PRE_SHUTDOWN);
        wa2.assertNextOperation(WorkbenchAdvisorObserver.POST_SHUTDOWN);
        wa2.assertAllOperationsExamined();
    }

    public void testCloseFromPostStartup() {

        WorkbenchAdvisorObserver wa = new WorkbenchAdvisorObserver(1) {
            public void postStartup() {
                super.postStartup();
                assertTrue(PlatformUI.getWorkbench().close());
            }
        };

        int code = PlatformUI.createAndRunWorkbench(display, wa);
        assertEquals(PlatformUI.RETURN_OK, code);

        wa.resetOperationIterator();
        wa.assertNextOperation(WorkbenchAdvisorObserver.INITIALIZE);
        wa.assertNextOperation(WorkbenchAdvisorObserver.PRE_STARTUP);
        wa.assertNextOperation(WorkbenchAdvisorObserver.PRE_WINDOW_OPEN);
        wa.assertNextOperation(WorkbenchAdvisorObserver.FILL_ACTION_BARS);
        wa.assertNextOperation(WorkbenchAdvisorObserver.POST_WINDOW_OPEN);
        wa.assertNextOperation(WorkbenchAdvisorObserver.POST_STARTUP);
        wa.assertNextOperation(WorkbenchAdvisorObserver.PRE_SHUTDOWN);
        wa.assertNextOperation(WorkbenchAdvisorObserver.POST_SHUTDOWN);
        wa.assertAllOperationsExamined();
    }

    public void testEventLoopCrash() {
        WorkbenchAdvisorExceptionObserver wa = new WorkbenchAdvisorExceptionObserver();

        int code = PlatformUI.createAndRunWorkbench(display, wa);
        assertEquals(PlatformUI.RETURN_OK, code);
        assertTrue(wa.exceptionCaught);
    }

    public void testFillAllActionBar() {
        WorkbenchAdvisorObserver wa = new WorkbenchAdvisorObserver(1) {

            public void fillActionBars(IWorkbenchWindow window,
                    IActionBarConfigurer configurer, int flags) {
                super.fillActionBars(window, configurer, flags);

                assertEquals(FILL_COOL_BAR, flags & FILL_COOL_BAR);
                assertEquals(FILL_MENU_BAR, flags & FILL_MENU_BAR);
                assertEquals(FILL_STATUS_LINE, flags & FILL_STATUS_LINE);
                assertEquals(0, flags & FILL_PROXY);
            }
        };

        int code = PlatformUI.createAndRunWorkbench(display, wa);
        assertEquals(PlatformUI.RETURN_OK, code);
    }
	
    public void testEmptyProgressRegion() {
        WorkbenchAdvisorObserver wa = new WorkbenchAdvisorObserver(1) {
			public void preWindowOpen(IWorkbenchWindowConfigurer configurer) {
				super.preWindowOpen(configurer);
				configurer.setShowProgressIndicator(false);
			}

			public void postWindowOpen(IWorkbenchWindowConfigurer configurer) {
				try {
					ProgressManagerUtil.animateUp(new Rectangle(0, 0, 100, 50));
				}
				catch (NullPointerException e) {
					assertTrue(false);
				}
			}
				
        };

        int code = PlatformUI.createAndRunWorkbench(display, wa);
        assertEquals(PlatformUI.RETURN_OK, code);
    }

}

class WorkbenchAdvisorExceptionObserver extends WorkbenchAdvisorObserver {

    public boolean exceptionCaught = false;

    private RuntimeException runtimeException;

    public WorkbenchAdvisorExceptionObserver() {
        super(2);
    }

    public void eventLoopIdle(Display disp) {
        super.eventLoopIdle(disp);

        if (runtimeException != null)
            return;

        runtimeException = new RuntimeException();
        throw runtimeException;
    }

    public void eventLoopException(Throwable exception) {

        exceptionCaught = true;
        Assert.assertEquals(runtimeException, exception);
    }
}
