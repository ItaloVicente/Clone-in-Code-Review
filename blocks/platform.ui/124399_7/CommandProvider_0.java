		if (!allCommandsRetrieved) {
			ICommandService commandService = getCommandService();
			Collection<String> commandIds = commandService.getDefinedCommandIds();
			for (String commandId : commandIds) {
				retrieveCommand(commandId);
			}
			allCommandsRetrieved = true;
		}
		return idToCommand.values().toArray(
				new QuickAccessElement[idToCommand.values().size()]);
	}

	private void retrieveCommand(final String currentCommandId) {
		boolean commandRetrieved = idToCommand.containsKey(currentCommandId);
		if (!commandRetrieved) {
