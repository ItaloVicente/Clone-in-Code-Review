
	public static UserPasswordCredentials getCredentials(final URIish uri,
			boolean rethrow) {
		try {
			return org.eclipse.egit.core.Activator.getDefault()
					.getSecureStore().getCredentials(uri);
		} catch (StorageException e) {
			Activator.logError(
					UIText.EGitCredentialsProvider_errorReadingCredentials, e);
			if (rethrow)
				throw new RuntimeException(e.getMessage(), e);
		}
		return null;
	}
