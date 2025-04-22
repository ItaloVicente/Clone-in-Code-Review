package org.eclipse.e4.ui.macros.internal.keybindings;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.e4.core.macros.EMacroService;
import org.eclipse.e4.ui.macros.internal.UserNotifications;
import org.eclipse.e4.ui.macros.internal.actions.ToggleMacroRecordAction;

public class CommandManagerExecutionListener implements IExecutionListener {

	private EMacroService fMacroService;

	private KeyBindingDispatcherInterceptor fInterceptor;

	public CommandManagerExecutionListener(EMacroService macroService, KeyBindingDispatcherInterceptor interceptor) {
		this.fMacroService = macroService;
		this.fInterceptor = interceptor;
	}

	@Override
	public void notHandled(String commandId, NotHandledException exception) {
		fInterceptor.clearLastCheckedCommand();
	}

	@Override
	public void postExecuteFailure(String commandId, ExecutionException exception) {
		fInterceptor.clearLastCheckedCommand();
	}

	@Override
	public void postExecuteSuccess(String commandId, Object returnValue) {
		if (fMacroService.isRecording()) {
			if (ToggleMacroRecordAction.COMMAND_ID.equals(commandId)) {
				return;
			}
			if (!fMacroService.isCommandWhitelisted(commandId)) {
				String message = String.format(Messages.CommandManagerExecutionListener_CommandNotRecorded, commandId);
				UserNotifications.showErrorMessage(message);
			} else {
				if (!commandId.equals(fInterceptor.getLastCheckedCommandId())) {
					String message = String.format(Messages.CommandManagerExecutionListener_CommandNotRecorded,
							commandId);
					UserNotifications.showErrorMessage(message);
				}
			}
		}
		fInterceptor.clearLastCheckedCommand();
	}

	@Override
	public void preExecute(String commandId, ExecutionEvent event) {
	}
}
