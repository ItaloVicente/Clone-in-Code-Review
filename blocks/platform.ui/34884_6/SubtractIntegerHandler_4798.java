
package org.eclipse.ui.tests.commands;

import org.eclipse.core.commands.AbstractHandlerWithState;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IStateListener;
import org.eclipse.core.commands.State;
import org.eclipse.core.commands.common.CommandException;
import org.eclipse.jface.commands.PersistentState;
import org.eclipse.jface.menus.TextState;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.handlers.RegistryRadioState;
import org.eclipse.ui.tests.TestPlugin;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class StateTest extends UITestCase {

	private static final String TEXT_HELLO = "hello";

	private static final class ObjectStateHandler extends
			AbstractHandlerWithState {

		Object currentValue;
		String textValue;

		@Override
		public final Object execute(final ExecutionEvent event) {
			getState(OBJECT_STATE_ID).setValue(OBJECT_CHANGED);
			return OBJECT_CHANGED;
		}

		@Override
		public final void handleStateChange(final State state,
				final Object oldValue) {
			if (OBJECT_STATE_ID.equals(state.getId())) {
				currentValue = state.getValue();
			} else if (TEXT_STATE_ID.equals(state.getId())) {
				textValue = (String) state.getValue();
			}
		}
	}

	private static final class StateListener implements IStateListener {
		Object currentValue;
		String textValue;

		@Override
		public final void handleStateChange(final State state,
				final Object oldValue) {

			if (OBJECT_STATE_ID.equals(state.getId())) {
				currentValue = state.getValue();
			} else if (TEXT_STATE_ID.equals(state.getId())) {
				textValue = (String) state.getValue();
			}
		}
	}

	private static final String COMMAND_ID = "org.eclipse.ui.tests.commandWithState";

	private static final Object OBJECT_CHANGED = "CHANGED";

	private static final Object OBJECT_INITIAL = "INITIAL";

	private static final String OBJECT_STATE_ID = "OBJECT";

	private static final String TEXT_STATE_ID = "TEXT";

	private ObjectStateHandler handler;

	private IHandlerActivation handlerActivation;

	public StateTest(String name) {
		super(name);
	}

	@Override
	protected final void doSetUp() {
		final ICommandService commandService = fWorkbench
				.getService(ICommandService.class);
		final Command command = commandService.getCommand(COMMAND_ID);
		command.getState(OBJECT_STATE_ID).setValue(OBJECT_INITIAL);
		command.getState(TEXT_STATE_ID).setValue(null);

		handler = new ObjectStateHandler();
		final IHandlerService handlerService = fWorkbench
				.getService(IHandlerService.class);
		handlerActivation = handlerService.activateHandler(COMMAND_ID, handler);
	}

	@Override
	protected final void doTearDown() {
		final IHandlerService handlerService = fWorkbench
				.getService(IHandlerService.class);
		handlerService.deactivateHandler(handlerActivation);
		handlerActivation = null;
		handler.dispose();
		handler = null;
	}

	public final void testCommandNotifiedOfStateChange()
			throws CommandException {
		final ICommandService commandService = fWorkbench
				.getService(ICommandService.class);
		final Command command = commandService.getCommand(COMMAND_ID);
		final State state = command.getState(OBJECT_STATE_ID);
		final StateListener listener = new StateListener();
		listener.currentValue = state.getValue();
		state.addListener(listener);

		assertSame("The initial state was not correct", OBJECT_INITIAL,
				listener.currentValue);

		final IHandlerService handlerService = fWorkbench
				.getService(IHandlerService.class);
		handlerService.executeCommand(COMMAND_ID, null);

		assertSame(
				"The state on the command after the handler changed was not correct",
				OBJECT_CHANGED, listener.currentValue);

		state.removeListener(listener);
	}

	public final void testStateChangeReflectedInCommand()
			throws CommandException {
		final ICommandService commandService = fWorkbench
				.getService(ICommandService.class);
		final Command command = commandService.getCommand(COMMAND_ID);

		assertSame("The initial state was not correct", OBJECT_INITIAL, command
				.getState(OBJECT_STATE_ID).getValue());

		final IHandlerService handlerService = fWorkbench
				.getService(IHandlerService.class);
		handlerService.executeCommand(COMMAND_ID, null);

		assertSame(
				"The state on the command after the handler changed was not correct",
				OBJECT_CHANGED, command.getState(OBJECT_STATE_ID).getValue());
	}

	public final void testStateChangeReflectedInHandler() {
		assertSame("The initial state was not correct", OBJECT_INITIAL,
				handler.currentValue);

		final ICommandService commandService = fWorkbench
				.getService(ICommandService.class);
		final Command command = commandService.getCommand(COMMAND_ID);
		command.getState(OBJECT_STATE_ID).setValue(OBJECT_CHANGED);

		assertSame(
				"The state on the command after the handler changed was not correct",
				OBJECT_CHANGED, handler.currentValue);
	}
	
	public final void testTextState() {
		assertNull(handler.textValue);
		final ICommandService commandService = fWorkbench
				.getService(ICommandService.class);
		final Command command = commandService.getCommand(COMMAND_ID);
		command.getState(TEXT_STATE_ID).setValue(TEXT_HELLO);
		assertEquals(TEXT_HELLO, handler.textValue);
	}
	
	public final void testTextStateListener() {
		assertNull(handler.textValue);
		final ICommandService commandService = fWorkbench
				.getService(ICommandService.class);
		final Command command = commandService.getCommand(COMMAND_ID);
		State state = command.getState(TEXT_STATE_ID);
		final StateListener listener = new StateListener();
		assertNull(state.getValue());
		assertNull(listener.textValue);
		assertNull(handler.textValue);
		
		state.addListener(listener);
		state.setValue(TEXT_HELLO);
		assertEquals(TEXT_HELLO, handler.textValue);
		assertEquals(TEXT_HELLO, listener.textValue);
	}
	
	public final void testTextPreference() {
		final ICommandService commandService = fWorkbench
				.getService(ICommandService.class);
		final Command command = commandService.getCommand(COMMAND_ID);
		State state = command.getState(TEXT_STATE_ID);
		state.setValue(TEXT_HELLO);
		assertTrue(state instanceof PersistentState);
		PersistentState pstate = (PersistentState) state;
		IPreferenceStore preferenceStore = TestPlugin.getDefault().getPreferenceStore();
		pstate.save(preferenceStore, COMMAND_ID
				+ "." + TEXT_STATE_ID);
		TextState nstate = new TextState();
		assertNull(nstate.getValue());
		nstate.load(preferenceStore, COMMAND_ID
				+ "." + TEXT_STATE_ID);
		assertEquals(TEXT_HELLO, nstate.getValue());
	}
	
	public final void testRadioState() {
		RegistryRadioState state1 = new RegistryRadioState();
		state1.setInitializationData(null, "class", COMMAND_ID);
		assertEquals(Boolean.FALSE, state1.getValue());
		RegistryRadioState state2 = new RegistryRadioState();
		state2.setInitializationData(null, "class", COMMAND_ID);
		assertEquals(Boolean.FALSE, state2.getValue());

		state1.setValue(Boolean.TRUE);
		assertEquals(Boolean.TRUE, state1.getValue());
		assertEquals(Boolean.FALSE, state2.getValue());

		state2.setValue(Boolean.TRUE);
		assertEquals(Boolean.FALSE, state1.getValue());
		assertEquals(Boolean.TRUE, state2.getValue());
	}
}
