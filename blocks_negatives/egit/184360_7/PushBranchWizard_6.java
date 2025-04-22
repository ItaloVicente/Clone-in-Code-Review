	private void storeCredentials(AddRemotePage remotePage) {
		if (remotePage.getStoreInSecureStore()) {
			URIish uri = remotePage.getSelection().getURI();
			if (uri != null)
				SecureStoreUtils.storeCredentials(
						remotePage.getCredentials(), uri);
		}
	}

	private void configureNewRemote(URIish uri) throws URISyntaxException,
			IOException {
		StoredConfig config = repository.getConfig();
		String remoteName = getRemoteName();
		RemoteConfig remoteConfig = new RemoteConfig(config, remoteName);
		remoteConfig.addURI(uri);
		RefSpec defaultFetchSpec = new RefSpec().setForceUpdate(true)
				.setSourceDestination(Constants.R_HEADS + "*", //$NON-NLS-1$
		remoteConfig.addFetchRefSpec(defaultFetchSpec);
		remoteConfig.update(config);
		config.save();
	}

