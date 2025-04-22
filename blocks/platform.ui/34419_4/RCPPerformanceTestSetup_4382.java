package org.eclipse.ui.tests.rcp.performance;

import org.eclipse.swt.widgets.Display;
import org.eclipse.test.performance.PerformanceTestCase;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.tests.rcp.util.WorkbenchAdvisorObserver;

public class PlatformUIPerfTest extends PerformanceTestCase {

    public void testCreateAndDisposeDisplayX100() {
        for (int i = 0; i < 10; ++i ) {
            startMeasuring();
            for (int j = 0; j < 100; ++j) {
            	Display display = PlatformUI.createDisplay();
                display.dispose();
                assertTrue(display.isDisposed());
            }
            stopMeasuring();
        }

        commitMeasurements();
        assertPerformance();
    }

    public void testRunAndShutdownWorkbench() {
        Display display = PlatformUI.createDisplay();

        for (int i = 0; i < 10; ++i ) {
            startMeasuring();
            int code = PlatformUI.createAndRunWorkbench(display, new WorkbenchAdvisorObserver(2));
            stopMeasuring();

            assertEquals(PlatformUI.RETURN_OK, code);
        }

        display.dispose();
        assertTrue(display.isDisposed());

        commitMeasurements();
        assertPerformance();
    }
}
