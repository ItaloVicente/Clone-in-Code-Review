package org.eclipse.ui.tests.rcp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.tests.rcp.util.WorkbenchAdvisorObserver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WorkbenchListenerTest {


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
	public void testPreAndPostShutdown() {
    	final boolean[] proceed = new boolean[1];
		final List<String> operations = new ArrayList<String>();
        WorkbenchAdvisor wa = new WorkbenchAdvisorObserver(1) {
        	@Override
			public void postStartup() {
        		IWorkbench workbench = getWorkbenchConfigurer().getWorkbench();
        		workbench.addWorkbenchListener(new IWorkbenchListener() {
					@Override
					public boolean preShutdown(IWorkbench workbench, boolean forced) {
						operations.add(PRE_SHUTDOWN);
						return proceed[0];
					}
					@Override
					public void postShutdown(IWorkbench workbench) {
						operations.add(POST_SHUTDOWN);
					}
				});
        		proceed[0] = false;
        		assertEquals(false, workbench.close());
        		proceed[0] = true;
        		assertEquals(true, workbench.close());
        	}
        };

        int code = PlatformUI.createAndRunWorkbench(display, wa);
        assertEquals(PlatformUI.RETURN_OK, code);

        assertEquals(3, operations.size());
        assertEquals(WorkbenchAdvisorObserver.PRE_SHUTDOWN, operations.get(0));
        assertEquals(WorkbenchAdvisorObserver.PRE_SHUTDOWN, operations.get(1));
        assertEquals(WorkbenchAdvisorObserver.POST_SHUTDOWN, operations.get(2));
    }


}
