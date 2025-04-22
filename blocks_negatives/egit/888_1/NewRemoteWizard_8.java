

			for (URIish uri : config.getURIs())
				RepositorySelectionPage.saveUriInPrefs(uri.toPrivateString());

			for (URIish uri : config.getPushURIs())
				RepositorySelectionPage.saveUriInPrefs(uri.toPrivateString());

