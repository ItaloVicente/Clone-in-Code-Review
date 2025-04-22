		if (repository == null)
			return false;

		Ref head = getHeadIfSymbolic(repository);
		if (head == null)
			return false;

		ICommandService commandService = (ICommandService) PlatformUI
				.getWorkbench().getService(ICommandService.class);
		if (commandService != null) {
			boolean isConfigured = SimpleConfigurePushDialog
					.getConfiguredRemote(repository) != null;
			Command command = commandService.getCommand(ActionCommands.SIMPLE_PUSH_ACTION);
			State name = command.getState(INamedHandleStateIds.NAME);
			String nameText = isConfigured ? UIText.SimplePushActionHandler_ConfiguredName : UIText.SimplePushActionHandler_UnconfiguredName;
			name.setValue(nameText);
		}

		return true;
	}

	private static Ref getHeadIfSymbolic(Repository repository) {
		try {
			Ref head = repository.getRef(Constants.HEAD);
			if (head != null && head.isSymbolic())
				return head;
			else
				return null;
		} catch (IOException e) {
			return null;
		}
