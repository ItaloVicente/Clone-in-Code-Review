package org.eclipse.jface.suites;

import org.eclipse.jface.widgets.TestUnitButtonFactory;
import org.eclipse.jface.widgets.TestUnitControlFactory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ //
	TestUnitControlFactory.class, //
	TestUnitButtonFactory.class })
public class AllUnitTests {

}
