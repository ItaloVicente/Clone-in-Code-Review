
package org.eclipse.ui.tests.commands;

import org.eclipse.core.commands.Category;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.CommandManager;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.ui.tests.harness.util.UITestCase;

public final class CommandManagerTest extends UITestCase {

	private final class ExecutionListener implements IExecutionListener {

		private String notHandledId = null;

		private String preExecuteId = null;

		private NotHandledException notHandledException = null;

		private ExecutionEvent preExecuteEvent = null;

		@Override
		public final void notHandled(final String commandId,
				final NotHandledException exception) {
			notHandledId = commandId;
			notHandledException = exception;
		}

		@Override
		public final void postExecuteFailure(final String commandId,
				final ExecutionException exception) {
		}

		@Override
		public final void postExecuteSuccess(final String commandId,
				final Object returnValue) {
		}

		@Override
		public final void preExecute(final String commandId,
				final ExecutionEvent event) {
			preExecuteId = commandId;
			preExecuteEvent = event;
		}

	}

	public CommandManagerTest(String testName) {
		super(testName);
	}

	public final void testExecutionListener() {
		final String commandId = "myCommand";
		final CommandManager commandManager = new CommandManager();
		final Category category = commandManager.getCategory(commandId);
		category.define("name", null);
		final Command command = commandManager.getCommand(commandId);
		command.define("name", null, category, null);
		final ExecutionListener listener = new ExecutionListener();
		commandManager.addExecutionListener(listener);
		Exception exception = null;
		final ExecutionEvent event = new ExecutionEvent();
		try {
			command.execute(event);
		} catch (final ExecutionException e) {
			exception = e;
		} catch (final NotHandledException e) {
			exception = e;
		}

		assertSame(
				"Should have received a pre-execute event for the correct command",
				commandId, listener.preExecuteId);
		assertSame(
				"Should have received a pre-execute event with the correct event",
				event, listener.preExecuteEvent);
		assertSame(
				"Should have received a not-handled event for the correct command",
				commandId, listener.notHandledId);
		assertSame(
				"Should have received a not-handled event with the correct exception",
				exception, listener.notHandledException);

	}
}
