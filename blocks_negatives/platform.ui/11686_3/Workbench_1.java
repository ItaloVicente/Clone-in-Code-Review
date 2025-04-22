		Command[] cmds = commandManager.getAllCommands();
		for (int i = 0; i < cmds.length; i++) {
			Command cmd = cmds[i];
			cmd.setHandler(new MakeHandlersGo(this, cmd.getId()));
		}

		commandManager.addCommandManagerListener(new ICommandManagerListener() {
			public void commandManagerChanged(CommandManagerEvent commandManagerEvent) {
				if (commandManagerEvent.isCommandDefined()) {
					Command cmd = commandManagerEvent.getCommandManager().getCommand(
							commandManagerEvent.getCommandId());
					cmd.setHandler(new MakeHandlersGo(Workbench.this, cmd.getId()));
				}
			}
		});
