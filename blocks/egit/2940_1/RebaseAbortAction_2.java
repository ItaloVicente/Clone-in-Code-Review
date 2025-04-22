package org.eclipse.egit.ui.internal.actions;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.Activator;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;

public class CommandActionHandler extends RepositoryActionHandler {

	private String commandName;

	public CommandActionHandler(String commandName) {
		this.commandName = commandName;
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow activeWorkbenchWindow = workbench
				.getActiveWorkbenchWindow();

		ICommandService commandService = (ICommandService) activeWorkbenchWindow
				.getService(ICommandService.class);
		Command continueCommand = commandService.getCommand(commandName);

		try {
			continueCommand.executeWithChecks(event);
		} catch (Exception e) {
			Activator.logError(e.getMessage(), e);
		}

		return null;
	}

}
