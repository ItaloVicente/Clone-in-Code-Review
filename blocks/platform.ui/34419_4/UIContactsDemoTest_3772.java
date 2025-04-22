
package org.eclipse.e4.ui.tests.application;

import junit.framework.Test;
import junit.framework.TestSuite;

public class StartupTestSuite extends TestSuite {

	public static Test suite() {
		TestSuite suite = new StartupTestSuite();

		suite.addTestSuite(EModelServiceTest.class);
		suite.addTestSuite(EModelServiceFindTest.class);
		suite.addTestSuite(EModelServiceInsertTest.class);
		suite.addTestSuite(EPartServiceTest.class);
		suite.addTestSuite(ESelectionServiceTest.class);
		suite.addTestSuite(EventBrokerTest.class);

		suite.addTestSuite(HeadlessContactsDemoTest.class);
		suite.addTestSuite(HeadlessPhotoDemoTest.class);


		suite.addTestSuite(UIEventsTest.class);

		return suite;
	}

}
