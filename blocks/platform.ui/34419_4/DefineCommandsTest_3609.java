package org.eclipse.e4.core.commands.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DefineCommandsTest.class, HandlerTest.class })
public class CommandTestSuite {

	public CommandTestSuite() {
	}
}
