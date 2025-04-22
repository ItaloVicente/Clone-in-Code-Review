		UserInteraction interaction = session.getUserInteraction();
		if (!interaction.isInteractionAllowed(session)) {
			return false;
		}
		String password = getPassword(session, interaction);
		if (password == null) {
			throw new AuthenticationCanceledException();
		}
		sendPassword(null, session, password, password);
		return true;
	}

	private String getPassword(ClientSession session,
			UserInteraction interaction) {
		String[] results = interaction.interactive(session, null, null, "", //$NON-NLS-1$
				new String[] { SshdText.get().passwordPrompt },
				new boolean[] { false });
		return (results == null || results.length == 0) ? null : results[0];
