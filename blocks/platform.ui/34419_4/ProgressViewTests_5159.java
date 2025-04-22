
package org.eclipse.ui.tests.progress;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ProgressTestSuite extends TestSuite {

	public static final Test suite() {
		return new ProgressTestSuite();
	}

	public ProgressTestSuite() {
		addTest(new TestSuite(ProgressContantsTest.class));
		addTest(new TestSuite(ProgressViewTests.class));
		addTest(new TestSuite(JobInfoTest.class));
		addTest(new TestSuite(JobInfoTestOrdering.class));
	}
}
