		throw new AuthenticationCanceledException();
	}

	@Override
	public String resolveAuthPasswordAttempt(ClientSession session)
			throws Exception {
		String[] results = interactive(session
				new String[] { SshdText.get().passwordPrompt }
				new boolean[] { false });
		return (results == null || results.length == 0) ? null : results[0];
