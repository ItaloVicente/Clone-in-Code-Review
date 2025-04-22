package org.eclipse.ui.tests.browser.internal;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {
	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for org.eclipse.ui.browser.tests");
		suite.addTestSuite(ExistenceTestCase.class);
		suite.addTestSuite(InternalBrowserViewTestCase.class);
		suite.addTestSuite(InternalBrowserEditorTestCase.class);
		
		suite.addTestSuite(DialogsTestCase.class);
		suite.addTestSuite(PreferencesTestCase.class);
		suite.addTestSuite(ToolbarBrowserTestCase.class);
		return suite;
	}
}
