

			for (URIish uri: myRemoteConfiguration.getURIs())
				RepositorySelectionPage.saveUriInPrefs(uri.toPrivateString());

			for (URIish uri: myRemoteConfiguration.getPushURIs())
				RepositorySelectionPage.saveUriInPrefs(uri.toPrivateString());


