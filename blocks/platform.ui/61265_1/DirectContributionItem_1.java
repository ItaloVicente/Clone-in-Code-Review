		if (helpService == null || commandHelpService == null) {
			return;
		}

		String contextHelpId = commandHelpService.getHelpContextId(getModel().getElementId(), getContext(getModel()));
		if (contextHelpId != null) {
			helpService.displayHelp(contextHelpId);
		}
