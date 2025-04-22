package org.eclipse.ui.tests.session;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.State;
import org.eclipse.jface.commands.PersistentState;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;

public class HandlerStateTest extends TestCase {

	public static TestSuite suite() {
		TestSuite ts = new TestSuite("org.eclipse.ui.tests.session.HandlerStateTest");
		ts.addTest(new HandlerStateTest("testInitialHandlerState"));
		ts.addTest(new HandlerStateTest("testModifiedHandlerState"));
		return ts;
	}

	private static final String COMMAND_ID = "org.eclipse.ui.tests.commandWithState";

	private static final String FALSE_STATE_ID = "FALSE";

	private static final String MODIFIED_TEXT = "Rain rain go away come back again in april or may";

	private static final String TEXT_STATE_ID = "TEXT";

	private static final String TRUE_STATE_ID = "TRUE";

	public HandlerStateTest(final String testName) {
		super(testName);
	}

	public final void testInitialHandlerState() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final ICommandService service = workbench
				.getService(ICommandService.class);
		final Command command = service.getCommand(COMMAND_ID);
		State state;
		boolean actual;

		state = command.getState(TRUE_STATE_ID);
		actual = ((Boolean) state.getValue()).booleanValue();
		assertTrue("The initial value should be true", actual);
		state.setValue(Boolean.FALSE);

		state = command.getState(FALSE_STATE_ID);
		actual = ((Boolean) state.getValue()).booleanValue();
		assertTrue("The initial value should be false", !actual);
		state.setValue(Boolean.TRUE);

		state = command.getState(TEXT_STATE_ID);
		final String text = (String) state.getValue();
		assertNull("The initial value should be null", text);
		((PersistentState) state).setShouldPersist(true);
		state.setValue(MODIFIED_TEXT);
	}

	public final void testModifiedHandlerState() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final ICommandService service = workbench
				.getService(ICommandService.class);
		final Command command = service.getCommand(COMMAND_ID);
		State state;
		boolean actual;

		state = command.getState(TRUE_STATE_ID);
		actual = ((Boolean) state.getValue()).booleanValue();
		assertTrue("The value should now be different", !actual);

		state = command.getState(FALSE_STATE_ID);
		actual = ((Boolean) state.getValue()).booleanValue();
		assertTrue("The value should now be different", actual);

		state = command.getState(TEXT_STATE_ID);
		((PersistentState) state).setShouldPersist(true);
		final String text = (String) state.getValue();
		assertEquals("The modified value was not persisted", MODIFIED_TEXT,
				text);
	}
}
