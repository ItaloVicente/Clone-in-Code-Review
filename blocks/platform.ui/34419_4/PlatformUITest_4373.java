package org.eclipse.ui.tests.rcp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.tests.rcp.util.EmptyView;
import org.eclipse.ui.tests.rcp.util.WorkbenchAdvisorObserver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IWorkbenchPageTest {


    private Display display = null;

	@Before
	public void setUp() throws Exception {

        assertNull(display);
        display = PlatformUI.createDisplay();
        assertNotNull(display);
    }

	@After
	public void tearDown() throws Exception {
        assertNotNull(display);
        display.dispose();
        assertTrue(display.isDisposed());

    }

	@Test
	public void test70080() {
        WorkbenchAdvisor wa = new WorkbenchAdvisorObserver(1) {

            @Override
			public void preWindowOpen(IWorkbenchWindowConfigurer configurer) {
                super.preWindowOpen(configurer);
                configurer.setShowPerspectiveBar(false);
            }

            @Override
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
