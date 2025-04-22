	private boolean setSafePassword(String p) {
		if (p != null && p.length() != 0) {
			password = p;
			passText.setText(p);
			return true;
		}
		return false;
	}

	private boolean setSafeUser(String u) {
		if (u != null && u.length() != 0) {
			user = u;
			userText.setText(u);
			return true;
		}
		return false;
	}

	private UserPasswordCredentials getSecureStoreCredentials(
			final URIish finalURI) throws StorageException {
		EGitSecureStore secureStore = org.eclipse.egit.core.Activator
				.getDefault().getSecureStore();
		UserPasswordCredentials credentials = secureStore
				.getCredentials(finalURI);
		return credentials;
	}

