		if(helpService==null)
			return;
		String helpContextId = getModel().getPersistedState().get(EHelpService.HELP_CONTEXT_ID);
		if (helpContextId != null) {
			helpService.displayHelp(helpContextId);
			return;
		}
