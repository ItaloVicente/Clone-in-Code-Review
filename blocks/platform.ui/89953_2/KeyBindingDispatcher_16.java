		boolean executedCommand = commandDefined && commandHandled;
		if (executedCommand) {
			if (fMacroContext.isRecording()) {
				Map<String, Boolean> macroAcceptedCommands = getMacroAcceptedCommands();
				Boolean recordActivation = macroAcceptedCommands.get(parameterizedCommand.getId());
				if (recordActivation) {
					MacroForParameterizedCommand macroCommand = new MacroForParameterizedCommand(parameterizedCommand,
							trigger);
					ContextInjectionFactory.inject(macroCommand, context);
					fMacroContext.addCommand(macroCommand);
				}
			}
		}
		return executedCommand;
