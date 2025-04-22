	public static boolean runCommand(String commandId,
			IStructuredSelection selection) {
		ICommandService commandService = (ICommandService) PlatformUI
				.getWorkbench().getService(ICommandService.class);
		Command cmd = commandService.getCommand(commandId);
		if (!cmd.isDefined())
			return false;

		IHandlerService handlerService = (IHandlerService) PlatformUI
				.getWorkbench().getService(IHandlerService.class);
		EvaluationContext c = null;
		if (selection != null) {
			c = new EvaluationContext(
					handlerService.createContextSnapshot(false),
					selection.toList());
			c.addVariable(ISources.ACTIVE_CURRENT_SELECTION_NAME, selection);
			c.removeVariable(ISources.ACTIVE_MENU_SELECTION_NAME);
		}
		try {
			if (c != null)
				handlerService.executeCommandInContext(
						new ParameterizedCommand(cmd, null), null, c);
			else
				handlerService.executeCommand(commandId, null);

			return true;
		} catch (CommandException ignored) {
		}
		return false;
	}

	private static LinkedList<String> splitIntoDigitAndNonDigitParts(
			String input) {
