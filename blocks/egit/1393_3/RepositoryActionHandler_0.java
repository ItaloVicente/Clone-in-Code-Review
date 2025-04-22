		Object selection = HandlerUtil.getActiveMenuSelection(event);
		if (selection == null)
			selection = HandlerUtil.getCurrentSelection(event);
		if (selection == null)
			throw new ExecutionException(
					UIText.RepositoryActionHandler_NoSelectionMessage);
