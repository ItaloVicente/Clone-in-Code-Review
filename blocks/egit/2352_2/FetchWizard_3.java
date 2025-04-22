
		if (repoSelection.isConfigSelected())
			op = new FetchOperationUI(localDb, repoSelection.getConfig()
					.getURIs().get(0), refSpecPage.getRefSpecs(), timeout,
					false);
		else
			op = new FetchOperationUI(localDb, repoSelection.getURI(false),
					refSpecPage.getRefSpecs(), timeout, false);

