package org.eclipse.e4.ui.macros.internal.keybindings;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.macros.EMacroContext;
import org.eclipse.e4.ui.bindings.keys.IKeyBindingDispatcherInterceptor;
import org.eclipse.e4.ui.macros.EAcceptedCommands;
import org.eclipse.swt.widgets.Event;

public class KeyBindingDispatcherInterceptor implements IKeyBindingDispatcherInterceptor {


	private IEclipseContext fEclipseContext;

	private EAcceptedCommands fAcceptedCommands;

	private EMacroContext fMacroContext;

	public KeyBindingDispatcherInterceptor(EMacroContext macroContext, EAcceptedCommands acceptedCommands,
			IEclipseContext eclipseContext) {
		this.fMacroContext = macroContext;
		this.fAcceptedCommands = acceptedCommands;
		this.fEclipseContext = eclipseContext;
	}

	@Override
	public void afterCommandExecuted(ParameterizedCommand parameterizedCommand, Event trigger, boolean commandDefined,
			boolean commandHandled) {
		boolean executedCommand = commandDefined && commandHandled;
		if (executedCommand) {
			if (fMacroContext.isRecording()) {
				if (fAcceptedCommands.isCommandRecorded(parameterizedCommand.getId())) {
					MacroForParameterizedCommand macroCommand = new MacroForParameterizedCommand(parameterizedCommand,
							trigger);
					ContextInjectionFactory.inject(macroCommand, fEclipseContext);
					fMacroContext.addMacroCommand(macroCommand);
				}
			}
		}
	}

	@Override
	public boolean interceptExecutePerfectMatch(ParameterizedCommand cmd, Event event) {
		if (fMacroContext == null || (!fMacroContext.isRecording() && !fMacroContext.isPlayingBack())
				|| fAcceptedCommands.isCommandAccepted(cmd.getId())) {
			return false;
		}
		return true;
	}
}
