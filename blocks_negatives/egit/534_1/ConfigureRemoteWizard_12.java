		RemoteConfig config;
		if (createMode) {

			SelectRemoteNamePage srp = (SelectRemoteNamePage) getPage(SelectRemoteNamePage.class
					.getName());

			String actRemoteName = srp.remoteName.getText();

			try {
				config = new RemoteConfig(myConfiguration, actRemoteName);
			} catch (URISyntaxException e1) {
				return false;
			}

			if (srp.configureFetch.getSelection()) {
				RepositorySelectionPage sp = (RepositorySelectionPage) getPages()[1];
				config.addURI(sp.getSelection().getURI());
				RefSpecPage specPage = (RefSpecPage) getPages()[2];
				config.setFetchRefSpecs(specPage.getRefSpecs());
				config.setTagOpt(specPage.getTagOpt());
				config.update(myConfiguration);
				sp.saveUriInPrefs(sp.getSelection().getURI().toString());

			}
			if (srp.configurePush.getSelection()) {
				RepositorySelectionPage sp = (RepositorySelectionPage) getPages()[3];
				config.addPushURI(sp.getSelection().getURI());
				RefSpecPage specPage = (RefSpecPage) getPages()[4];
				config.setPushRefSpecs(specPage.getRefSpecs());
				config.update(myConfiguration);
				sp.saveUriInPrefs(sp.getSelection().getURI().toString());
			}

