			commandListener = new ICommandListener() {
				@Override
				public void commandChanged(CommandEvent commandEvent) {
					if (commandEvent.isHandledChanged()
							|| commandEvent.isEnabledChanged()) {
						if (commandEvent.getCommand().isDefined()) {
							setEnabled(commandEvent.getCommand().isEnabled());
						}
