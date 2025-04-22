	private UserPasswordCredentials getSecureStoreCredentials(
			final URIish finalURI) throws StorageException {
		EGitSecureStore secureStore = org.eclipse.egit.core.Activator
				.getDefault().getSecureStore();
		UserPasswordCredentials credentials = secureStore
				.getCredentials(finalURI);
		return credentials;
	}

