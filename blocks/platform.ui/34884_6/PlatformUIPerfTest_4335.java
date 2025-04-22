package org.eclipse.ui.tests.rcp.performance;

import org.eclipse.swt.widgets.Display;
import org.eclipse.test.performance.Dimension;
import org.eclipse.test.performance.Performance;
import org.eclipse.test.performance.PerformanceMeter;
import org.eclipse.test.performance.PerformanceTestCase;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.tests.rcp.util.OpenWorkbenchIntervalMonitor;
import org.eclipse.ui.tests.rcp.util.RestoreWorkbenchIntervalMonitor;

public class EmptyWorkbenchPerfTest extends PerformanceTestCase {

    private static final int REPEAT_COUNT = 25;

    public void testOpen() {
        Display display = PlatformUI.createDisplay();
        Performance perf = Performance.getDefault();
        String baseScenarioId = perf.getDefaultScenarioId(this);
        PerformanceMeter startupMeter = perf.createPerformanceMeter( baseScenarioId + " [open]"); 
        PerformanceMeter shutdownMeter = perf.createPerformanceMeter( baseScenarioId + " [close]"); 

        tagAsSummary("Open RCP App", Dimension.CPU_TIME);
        for (int i = 0; i < REPEAT_COUNT; ++i ) {
        	startupMeter.start();
            int code = PlatformUI.createAndRunWorkbench(display,
            		new OpenWorkbenchIntervalMonitor(startupMeter, shutdownMeter));
            shutdownMeter.stop();
            assertEquals(PlatformUI.RETURN_OK, code);
        }

        display.dispose();
        assertTrue(display.isDisposed());
        startupMeter.commit();
        perf.assertPerformance(startupMeter);
        
        perf.assertPerformanceInAbsoluteBand(shutdownMeter, Dimension.CPU_TIME, 0, 120);
        
    	startupMeter.dispose();
    	shutdownMeter.dispose();
    }

    public void testRestore() {
        Display display = PlatformUI.createDisplay();
        Performance perf = Performance.getDefault();
        String baseScenarioId = perf.getDefaultScenarioId(this);
        PerformanceMeter startupMeter = perf.createPerformanceMeter( baseScenarioId + " [open]"); 
        PerformanceMeter shutdownMeter = perf.createPerformanceMeter( baseScenarioId + " [close]");
        
        PerformanceMeter startupMeter0 = perf.createPerformanceMeter( baseScenarioId + " [0][open]"); 
        PerformanceMeter shutdownMeter0 = perf.createPerformanceMeter( baseScenarioId + " [0][close]");
        WorkbenchAdvisor wa = new RestoreWorkbenchIntervalMonitor(startupMeter0, shutdownMeter0, true);
        int code = PlatformUI.createAndRunWorkbench(display, wa);
        assertEquals(PlatformUI.RETURN_RESTART, code);
        assertFalse(display.isDisposed());
    	startupMeter0.dispose();
    	shutdownMeter0.dispose();
       
        tagAsSummary("Restore RCP App", Dimension.CPU_TIME);
        
        for (int i = 0; i < REPEAT_COUNT; ++i ) {
        	startupMeter.start();
            code = PlatformUI.createAndRunWorkbench(display,
                    new RestoreWorkbenchIntervalMonitor(startupMeter, shutdownMeter, false));
            shutdownMeter.stop();
            assertEquals(PlatformUI.RETURN_OK, code);
        }
        
        display.dispose();
        assertTrue(display.isDisposed());
        
        startupMeter.commit();
        perf.assertPerformance(startupMeter);
        
        perf.assertPerformanceInAbsoluteBand(shutdownMeter, Dimension.CPU_TIME, 0, 120);
        
    	startupMeter.dispose();
    	shutdownMeter.dispose();
    }
}
