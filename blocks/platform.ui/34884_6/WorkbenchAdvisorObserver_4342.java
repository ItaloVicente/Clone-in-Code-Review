package org.eclipse.ui.tests.rcp.util;

import org.eclipse.swt.widgets.Display;
import org.eclipse.test.performance.PerformanceMeter;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.tests.harness.util.RCPTestWorkbenchAdvisor;


public class RestoreWorkbenchIntervalMonitor extends RCPTestWorkbenchAdvisor {

    private PerformanceMeter startupMeter; 
    private PerformanceMeter shutdownMeter; 
	
	private boolean createRestorableWorkbench = false;
	
    private IWorkbenchConfigurer workbenchConfigurer;

    public RestoreWorkbenchIntervalMonitor(PerformanceMeter startupMeter, PerformanceMeter shutdownMeter, boolean createRestorableWorkbench) {
        super(2);
        this.startupMeter = startupMeter;
        this.shutdownMeter = shutdownMeter;
        this.createRestorableWorkbench = createRestorableWorkbench;
    }

    public void initialize(IWorkbenchConfigurer configurer) {
        super.initialize(configurer);
        workbenchConfigurer = configurer;
        workbenchConfigurer.setSaveAndRestore(true);
    }

    public void postStartup() {
    	startupMeter.stop();
        super.postStartup();
    }

    public boolean preShutdown() {
        boolean ret = super.preShutdown();
        shutdownMeter.start();
        return ret;
    }

    public void eventLoopIdle(Display d) {
        if (createRestorableWorkbench)
            workbenchConfigurer.getWorkbench().restart();
        else
            super.eventLoopIdle(d);
    }
}
