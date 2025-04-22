package org.eclipse.egit.ui.test;

import org.eclipse.egit.ui.test.nonswt.AllNonSWTTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AllNonSWTTests.class, 
	AllLocalTests.class 
})

public class AllSWTAndNonSWTTests {
}
