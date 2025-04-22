
		URIish uriToCheck;
		if (pushMode) {
			if (config.getPushURIs().isEmpty())
				uriToCheck = config.getURIs().get(0);
			else
				uriToCheck = config.getPushURIs().get(0);
		} else
			uriToCheck = config.getURIs().get(0);

		final RefContentAssistProvider assistProvider = new RefContentAssistProvider(
				repo, uriToCheck, getShell());

