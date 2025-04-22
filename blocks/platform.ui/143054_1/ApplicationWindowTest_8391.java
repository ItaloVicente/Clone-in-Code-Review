
package org.eclipse.jface.tests.window;

import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ApplicationWindowTest.class })
public class AllTests {

	public static void main(String[] args) {
		JUnitCore.main(AllTests.class.getName());
	}
}
