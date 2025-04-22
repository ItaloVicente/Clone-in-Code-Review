
	public static UserPasswordCredentials getCredentials(final URIish uri) {
		try {
			return org.eclipse.egit.core.Activator.getDefault()
					.getSecureStore().getCredentials(uri);
		} catch (StorageException e) {
			Activator.logError(
					UIText.EGitCredentialsProvider_errorReadingCredentials, e);
		}
		return null;
	}
