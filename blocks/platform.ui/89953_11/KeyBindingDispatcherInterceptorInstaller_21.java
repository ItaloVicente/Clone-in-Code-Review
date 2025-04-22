package org.eclipse.e4.ui.macros.internal.keybindings;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.e4.core.macros.Activator;
import org.eclipse.e4.core.macros.EMacroService;
import org.eclipse.e4.ui.bindings.keys.IKeyBindingInterceptor;
import org.eclipse.e4.ui.bindings.keys.KeyBindingDispatcher;
import org.eclipse.swt.widgets.Event;

public class KeyBindingDispatcherInterceptor implements IKeyBindingInterceptor {


	private EMacroService fMacroService;

	private KeyBindingDispatcher fKeybindingDispatcher;

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
					MacroInstructionForParameterizedCommand macroInstruction = new MacroInstructionForParameterizedCommand(parameterizedCommand,
							trigger, fKeybindingDispatcher);
					fMacroService.addMacroInstruction(macroInstruction, trigger, EMacroService.PRIORITY_HIGH);
				}
			}
		}
	}

	@Override
	public boolean executeCommand(ParameterizedCommand cmd, Event event) {
		if (fMacroService == null || (!fMacroService.isRecording() && !fMacroService.isPlayingBack())
				|| fMacroService.isCommandWhitelisted(cmd.getId())) {
			return false;
		}
		Activator plugin = Activator.getDefault();
		if (plugin != null) {
			plugin.getLog().log(new Status(IStatus.INFO, plugin.getBundle().getSymbolicName(),
					"Skipping execution of command not whitelisted in macro: " + cmd.getId())); //$NON-NLS-1$
		}
		return true;
	}
}
