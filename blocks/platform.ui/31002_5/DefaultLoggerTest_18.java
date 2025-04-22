package org.eclipse.ui.internal.monitoring;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllMonitoringTests {
	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTestSuite(EventLoopMonitorThreadTests.class);
		suite.addTestSuite(FilterHandlerTests.class);
		suite.addTestSuite(DefaultLoggerTest.class);
		return suite;
	}
}
