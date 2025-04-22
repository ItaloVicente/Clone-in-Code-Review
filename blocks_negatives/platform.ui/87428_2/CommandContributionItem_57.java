			commandListener = new ICommandListener() {
				@Override
				public void commandChanged(CommandEvent commandEvent) {
					if (commandEvent.isHandledChanged()
							|| commandEvent.isEnabledChanged()
							|| commandEvent.isDefinedChanged()) {
						updateCommandProperties(commandEvent);
					}
