
package org.eclipse.jface.tests.window;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests extends TestSuite {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		return new AllTests();
	}

	public AllTests() {
		addTestSuite(ApplicationWindowTest.class);
	}

}
