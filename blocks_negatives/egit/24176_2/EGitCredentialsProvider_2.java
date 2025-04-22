
	private UserPasswordCredentials getCredentialsFromSecureStore(final URIish uri) {
		UserPasswordCredentials credentials = null;
		try {
			credentials = Activator.getDefault().getSecureStore()
					.getCredentials(uri);
		} catch (StorageException e) {
			Activator.logError(
					UIText.EGitCredentialsProvider_errorReadingCredentials, e);
		}
		return credentials;
	}
