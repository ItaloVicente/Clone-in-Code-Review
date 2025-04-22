package org.eclipse.ui.tests.rcp;

import junit.framework.TestCase;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.tests.rcp.util.EmptyView;
import org.eclipse.ui.tests.rcp.util.WorkbenchAdvisorObserver;

public class IWorkbenchPageTest extends TestCase {

    public IWorkbenchPageTest(String name) {
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

    public void test70080() {
        WorkbenchAdvisor wa = new WorkbenchAdvisorObserver(1) {

            public void preWindowOpen(IWorkbenchWindowConfigurer configurer) {
                super.preWindowOpen(configurer);
                configurer.setShowPerspectiveBar(false);
            }

            public void postStartup() {
                try {
                    IWorkbenchWindow window = getWorkbenchConfigurer()
                            .getWorkbench().getActiveWorkbenchWindow();
                    IWorkbenchPage page = window.getActivePage();
                    page.showView(EmptyView.ID);
                    assertNotNull(page.findView(EmptyView.ID));
                    page.resetPerspective();
                    assertNull(page.findView(EmptyView.ID));
                } catch (PartInitException e) {
                    fail(e.toString());
                }
            }
        };
        int code = PlatformUI.createAndRunWorkbench(display, wa);
        assertEquals(PlatformUI.RETURN_OK, code);
    }

}
