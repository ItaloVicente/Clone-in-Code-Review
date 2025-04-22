package org.eclipse.e4.ui.macros.internal.keybindings;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.macros.EMacroService;
import org.eclipse.e4.ui.bindings.keys.IKeyBindingInterceptor;
import org.eclipse.e4.ui.bindings.keys.KeyBindingDispatcher;
import org.eclipse.e4.ui.macros.internal.UserNotifications;
import org.eclipse.swt.widgets.Event;

public class KeyBindingDispatcherInterceptor implements IKeyBindingInterceptor {

	private EMacroService fMacroService;

	private KeyBindingDispatcher fKeybindingDispatcher;

	private String fLastCheckedCommandId;

	public KeyBindingDispatcherInterceptor(EMacroService macroService, KeyBindingDispatcher dispatcher) {
		this.fMacroService = macroService;
		this.fKeybindingDispatcher = dispatcher;
	}

	@Override
	public void postExecuteCommand(ParameterizedCommand parameterizedCommand, Event trigger, boolean commandDefined,
			boolean commandHandled) {
		boolean executedCommand = commandDefined && commandHandled;
		if (executedCommand) {
			if (fMacroService.isRecording()) {
				if (fMacroService.getRecordMacroInstruction(parameterizedCommand.getId())) {
					MacroInstructionForParameterizedCommand macroInstruction = new MacroInstructionForParameterizedCommand(
							parameterizedCommand, trigger, fKeybindingDispatcher);
					fMacroService.addMacroInstruction(macroInstruction, trigger, EMacroService.PRIORITY_HIGH);
				}
			}
		}
	}

	@Override
	public boolean executeCommand(ParameterizedCommand cmd, Event event) {
		this.fLastCheckedCommandId = cmd.getId();
		if (fMacroService == null || (!fMacroService.isRecording() && !fMacroService.isPlayingBack())
				|| fMacroService.isCommandWhitelisted(cmd.getId())) {
			return false;
		}
		UserNotifications.showErrorMessage(
				String.format(Messages.KeyBindingDispatcherInterceptor_SkipExecutionOfCommand, cmd.getId()));
		return true;
	}

	public String getLastCheckedCommandId() {
		return fLastCheckedCommandId;
	}

	public void clearLastCheckedCommand() {
		fLastCheckedCommandId = null;
	}
}
