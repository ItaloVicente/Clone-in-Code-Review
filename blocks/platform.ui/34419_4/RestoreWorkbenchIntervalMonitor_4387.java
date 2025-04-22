package org.eclipse.ui.tests.rcp.util;

import org.eclipse.test.performance.PerformanceMeter;
import org.eclipse.ui.tests.harness.util.RCPTestWorkbenchAdvisor;


public class OpenWorkbenchIntervalMonitor extends RCPTestWorkbenchAdvisor {

    private PerformanceMeter startupMeter;
    private PerformanceMeter shutdownMeter;

    public OpenWorkbenchIntervalMonitor(PerformanceMeter startupMeter, PerformanceMeter shutdownMeter) {
        super(2);
        this.startupMeter = startupMeter;
        this.shutdownMeter = shutdownMeter;
    }

    @Override
	public void postStartup() {
    	startupMeter.stop();
        super.postStartup();
    }

    @Override
	public boolean preShutdown() {
    	shutdownMeter.start();
        return super.preShutdown();
    }
}
