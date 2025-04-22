			while (!myRemoteConfiguration.getURIs().isEmpty())
				myRemoteConfiguration.removeURI(myRemoteConfiguration.getURIs()
						.get(0));
			myRemoteConfiguration.addURI(configureFetchUriPage.getUri());
			myRemoteConfiguration.setFetchRefSpecs(configureFetchSpecPage
					.getRefSpecs());
			myRemoteConfiguration.setTagOpt(configureFetchSpecPage.getTagOpt());
		}
