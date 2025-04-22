
package org.eclipse.ui.tests.leaks;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		LeakTests.class,
		Bug397302Tests.class,
})
public class LeaksTestSuite {
}
