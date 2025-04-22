	@Override
	protected ParameterizedCommand getForwardCommand() {
		final ICommandService commandService = window.getWorkbench().getService(ICommandService.class);
		final Command command = commandService.getCommand(ORG_ECLIPSE_UI_WINDOW_OPEN_EDITOR_DROP_DOWN);
		ParameterizedCommand commandF = new ParameterizedCommand(command, null);
		return commandF;
	}

