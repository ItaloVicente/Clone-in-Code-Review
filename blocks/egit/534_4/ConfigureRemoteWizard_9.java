		if (pushMode) {
			while (!myRemoteConfiguration.getPushURIs().isEmpty())
				myRemoteConfiguration.removePushURI(myRemoteConfiguration
						.getPushURIs().get(0));
			for (URIish uri : configurePushUriPage.getUris())
				myRemoteConfiguration.addPushURI(uri);
			myRemoteConfiguration.setPushRefSpecs(configurePushSpecPage
					.getRefSpecs());
