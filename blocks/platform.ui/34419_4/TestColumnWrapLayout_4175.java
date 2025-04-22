
package org.eclipse.ui.tests.forms.layout;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllLayoutTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"org.eclipse.ua.tests.forms.AllLayoutTests");
		suite.addTestSuite(TestTableWrapLayout.class);
		suite.addTestSuite(TestColumnWrapLayout.class);
		return suite;
	}

}
