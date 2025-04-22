package org.eclipse.egit.ui.internal.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.dialogs.GitTraceConfigurationDialog;
import org.eclipse.ui.handlers.HandlerUtil;

public class ConfigureDebugTraceCommand extends AbstractHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		new GitTraceConfigurationDialog(HandlerUtil
				.getActiveShellChecked(event)).open();
		return null;
	}
}
