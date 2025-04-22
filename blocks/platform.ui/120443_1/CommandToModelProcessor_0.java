			final MCommand mCommand = commands.get(cmd.getId());
			if (mCommand != null) {
				try {
					String cmdName = cmd.getName();
					if (!cmdName.equals(mCommand.getCommandName())) {
						mCommand.setCommandName(cmdName);
						String cmdDesc = cmd.getDescription();
						if (cmdDesc != null)
							mCommand.setDescription(cmdDesc);
					}
				} catch (NotDefinedException e) {
					WorkbenchPlugin.log(e);
				}
