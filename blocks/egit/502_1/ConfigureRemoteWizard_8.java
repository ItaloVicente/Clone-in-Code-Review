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
