		if (calledFromRepoPage)
			op = new FetchOperationUI(localDb, repoSelection.getConfig(),
					false);
		else if (repoSelection.isConfigSelected())
			op = new FetchOperationUI(localDb, repoSelection.getConfig()
					.getURIs().get(0), refSpecPage.getRefSpecs(),
					false);
		else
			op = new FetchOperationUI(localDb, repoSelection.getURI(false),
					refSpecPage.getRefSpecs(), false);
