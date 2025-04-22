package org.eclipse.ui.tests.rcp.performance;

import junit.framework.Test;
import junit.framework.TestSuite;

public class RCPPerformanceTestSuite extends TestSuite {

    public static Test suite() {
    	return new RCPPerformanceTestSetup(new RCPPerformanceTestSuite());
    }

    public RCPPerformanceTestSuite() {
        addTestSuite(PlatformUIPerfTest.class);
        addTestSuite(EmptyWorkbenchPerfTest.class);
    }
}
