package org.eclipse.egit.ui.performance;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Performance tests for EGit");
		suite.addTestSuite(SynchronizeWithStagedChangesPerformanceTest.class);
		suite.addTestSuite(SynchronizeWithoutLocalChangesPerformanceTest.class);
		suite.addTestSuite(SynchronizeWithWorkingTreeChangesPerformanceTest.class);

		return suite;
	}

}
