	protected void handleHelpRequest() {
		if (helpService == null)
			return;
		String helpContextId = getModel().getPersistedState().get(HELP_CONTEXT_ID);
		if (helpContextId != null)
			helpService.displayHelp(helpContextId);
	}
