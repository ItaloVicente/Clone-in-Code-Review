				try { // This is needed to set the command name and description using the correct locale.
					String cmdName = cmd.getName();
					MCommand mCommand = commands.get(cmd.getId());
					if (mCommand != null && cmdName != null && !cmdName.equals(mCommand.getCommandName())) {
						mCommand.setCommandName(cmdName);
						mCommand.setDescription(cmd.getDescription());
					}
				} catch (NotDefinedException e) {
				}
