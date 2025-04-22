	@SuppressWarnings("restriction")
	private void handleHelpRequest() {
		MCommand command = model.getCommand();
		if (command != null) {
			String contextHelpId = commandHelpService.getHelpContextId(
					command.getElementId(), getContext(model));
			if (contextHelpId != null) {
				helpService.displayHelp(contextHelpId);
			}
		}
	}

