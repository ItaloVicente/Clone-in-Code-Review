
	private static void storeCredentials(URIish uri,
			UserPasswordCredentials credentials) {
		try {
			org.eclipse.egit.core.Activator.getDefault().getSecureStore()
					.putCredentials(uri, credentials);
		} catch (StorageException e) {
			Activator.handleError(UIText.LoginService_storingCredentialsFailed, e, true);
		} catch (IOException e) {
			Activator.handleError(UIText.LoginService_storingCredentialsFailed, e, true);
		}
	}

	private static UserPasswordCredentials getCredentialsFromSecureStore(final URIish uri) {
		UserPasswordCredentials credentials = null;
		try {
			credentials = org.eclipse.egit.core.Activator.getDefault().getSecureStore()
					.getCredentials(uri);
		} catch (StorageException e) {
			Activator.logError(
					UIText.LoginService_readingCredentialsFailed, e);
		}
		return credentials;
	}

