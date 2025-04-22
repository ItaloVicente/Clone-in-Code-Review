	private void setPassword(String p) {
		password = p;
		passText.setText(p);
	}

	private void setUser(String u) {
		user = u;
		userText.setText(u);
	}

	private UserPasswordCredentials getSecureStoreCredentials(
			final URIish finalURI) throws StorageException {
		EGitSecureStore secureStore = org.eclipse.egit.core.Activator
				.getDefault().getSecureStore();
		UserPasswordCredentials credentials = secureStore
				.getCredentials(finalURI);
		return credentials;
	}

