package org.eclipse.ui.tests.commands;

import junit.framework.Test;
import junit.framework.TestSuite;

public final class CommandsTestSuite extends TestSuite {

	public static final Test suite() {
		return new CommandsTestSuite();
	}

	public CommandsTestSuite() {
		addTest(new TestSuite(CommandExecutionTest.class));
		addTest(new TestSuite(Bug66182Test.class));
		addTest(new TestSuite(Bug70503Test.class));
		addTest(new TestSuite(Bug73756Test.class));
		addTest(new TestSuite(Bug74982Test.class));
		addTest(new TestSuite(Bug74990Test.class));
		addTest(new TestSuite(Bug87856Test.class));
		addTest(new TestSuite(Bug125792Test.class));
		addTest(new TestSuite(Bug417762Test.class));
		addTest(new TestSuite(CommandManagerTest.class));
		addTest(new TestSuite(CommandParameterTypeTest.class));
		addTest(new TestSuite(CommandSerializationTest.class));
		addTest(new TestSuite(HelpContextIdTest.class));
		addTest(new TestSuite(StateTest.class));
		addTest(new TestSuite(HandlerActivationTest.class));
		addTest(new TestSuite(CommandCallbackTest.class));
		addTest(new TestSuite(CommandEnablementTest.class));
		addTest(new TestSuite(CommandActionTest.class));
		addTest(new TestSuite(ActionDelegateProxyTest.class));
		addTest(ToggleStateTest.suite());
		addTest(new TestSuite(RadioStateTest.class));
	}
}
