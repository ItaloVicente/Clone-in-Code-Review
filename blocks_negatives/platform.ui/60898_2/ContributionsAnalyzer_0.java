		final List<MCommand> cmds = app.getCommands();
		for (MCommand cmd : cmds) {
			if (cmdId.equals(cmd.getElementId())) {
				return cmd;
			}
		}
		return null;
