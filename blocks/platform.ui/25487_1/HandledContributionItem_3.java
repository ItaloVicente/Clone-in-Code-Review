	@SuppressWarnings("restriction")
	private void handleHelpRequest() {
		if (model.getCommand() == null) {
			return;
		}

		String commandId = model.getCommand().getElementId();
		String contextHelpId = null;
		try {
			contextHelpId = commandHelpService.getHelpContextId(commandId,
					getContext(model));
		} catch (NotDefinedException e) {
			if (logger != null) {
				logger.error(e, "A command with ID " + commandId //$NON-NLS-1$
						+ " is not defined. Context help cannot be displayed."); //$NON-NLS-1$
			}
			return;
		}
		if (contextHelpId != null) {
			helpService.displayHelp(contextHelpId);
		}
	}

