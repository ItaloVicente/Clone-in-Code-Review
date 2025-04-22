				if (fMacroContext == null || (!fMacroContext.isRecording() && !fMacroContext.isPlayingBack())
						|| getMacroAcceptedCommands().containsKey(cmd.getId())) {
					try {
						return executeCommand(cmd, event) || !sequenceBeforeKeyStroke.isEmpty();
					} catch (final CommandException e) {
						return true;
					}
