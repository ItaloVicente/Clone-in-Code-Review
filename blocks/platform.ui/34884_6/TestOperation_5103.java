
package org.eclipse.ui.tests.operations;

import junit.framework.Test;
import junit.framework.TestSuite;

public class OperationsTestSuite extends TestSuite {
	public static final Test suite() {
		return new OperationsTestSuite();
	}

	public OperationsTestSuite() {
		addTest(new TestSuite(OperationsAPITest.class));
		addTest(new TestSuite(WorkbenchOperationHistoryTests.class));
		addTest(new TestSuite(MultiThreadedOperationsTests.class));
		addTest(new TestSuite(WorkbenchOperationStressTests.class));
		addTest(new TestSuite(WorkspaceOperationsTests.class));
	}
}
