package org.eclipse.ui.tests.services;

import junit.framework.Test;
import junit.framework.TestSuite;

public final class ServicesTestSuite extends TestSuite {

	public static final Test suite() {
		return new ServicesTestSuite();
	}

	public ServicesTestSuite() {
		addTest(new TestSuite(EvaluationServiceTest.class));
		addTest(ContributedServiceTest.suite());
		addTest(new TestSuite(WorkbenchSiteProgressServiceTest.class));
	}
}
