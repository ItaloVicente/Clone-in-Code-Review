package org.eclipse.e4.ui.macros.internal.keybindings;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.macros.Activator;
import org.eclipse.e4.core.macros.EMacroService;
import org.eclipse.e4.ui.bindings.keys.IKeyBindingInterceptor;
import org.eclipse.swt.widgets.Event;

public class KeyBindingDispatcherInterceptor implements IKeyBindingInterceptor {


	private IEclipseContext fEclipseContext;

	private EMacroService fMacroService;

	public KeyBindingDispatcherInterceptor(EMacroService macroService, IEclipseContext eclipseContext) {
		this.fMacroService = macroService;
		this.fEclipseContext = eclipseContext;
	}

	@Override
	public void postExecuteCommand(ParameterizedCommand parameterizedCommand, Event trigger, boolean commandDefined,
			boolean commandHandled) {
		boolean executedCommand = commandDefined && commandHandled;
		if (executedCommand) {
			if (fMacroService.isRecording()) {
				if (fMacroService.getRecordMacroInstruction(parameterizedCommand.getId())) {
					MacroInstructionForParameterizedCommand macroInstruction = new MacroInstructionForParameterizedCommand(parameterizedCommand,
							trigger);
					ContextInjectionFactory.inject(macroInstruction, fEclipseContext);
					fMacroService.addMacroInstruction(macroInstruction);
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
