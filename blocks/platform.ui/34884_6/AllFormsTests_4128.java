package org.eclipse.ui.tests.forms;

import org.eclipse.ui.tests.forms.performance.FormsPerformanceTest;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllFormsPerformanceTests extends TestSuite {

	public static Test suite() {
		return new AllFormsPerformanceTests();
	}

	public AllFormsPerformanceTests() {
		addTestSuite(FormsPerformanceTest.class);
	}
}
