package org.eclipse.e4.ui.bindings.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import junit.framework.TestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	BindingLookupTest.class,
	KeyDispatcherTest.class,
	BindingTableTests.class,
	BindingCreateTest.class })
public class BindingTestSuite extends TestSuite {
}
