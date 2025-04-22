
package org.eclipse.ui.tests.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.tests.harness.util.UITestCase;

public final class HelpContextIdTest extends UITestCase {

	private static final String COMMAND_HELP_ID = "org.eclipse.ui.tests.commandHelp";

	private static final String COMMAND_ID = "org.eclipse.ui.tests.helpContextId";

	private static final String HANDLER_HELP_ID = "org.eclipse.ui.tests.handlerHelp";

	private static final String NEW_HELP_ID = "new_help_id";

	public HelpContextIdTest(final String testName) {
		super(testName);
	}

	public final void testHelpContextId() throws NotDefinedException {
		final ICommandService commandService = fWorkbench
				.getService(ICommandService.class);
		final IHandlerService handlerService = fWorkbench
				.getService(IHandlerService.class);
		String helpContextId = null;

		helpContextId = commandService.getHelpContextId(COMMAND_ID);
		assertEquals(
				"The initial help context id should be that of the handler",
				HANDLER_HELP_ID, helpContextId);

		handlerService.activateHandler(COMMAND_ID, new AbstractHandler() {
			@Override
			public final Object execute(final ExecutionEvent event) {
				return null;
			}
		});
		helpContextId = commandService.getHelpContextId(COMMAND_ID);
		assertEquals("The help context id should now be that of the command",
				COMMAND_HELP_ID, helpContextId);

		final Command command = commandService.getCommand(COMMAND_ID);
		command.undefine();
		command.define("New Name", null, commandService.getCategory(null),
				null, null, NEW_HELP_ID);
		helpContextId = commandService.getHelpContextId(COMMAND_ID);
		assertEquals("The help context id should now be the new id",
				NEW_HELP_ID, helpContextId);
	}
}

