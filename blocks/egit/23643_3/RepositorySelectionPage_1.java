	private boolean setSafePassword(String p) {
		if ((password == null || password.length() == 0) && p != null
				&& p.length() != 0) {
			password = p;
			passText.setText(p);
			return true;
		}
		return false;
	}

	private boolean setSafeUser(String u) {
		if ((user == null || user.length() == 0) && u != null
				&& u.length() != 0) {
			user = u;
			userText.setText(u);
			return true;
		}
		return false;
	}

	private void setStoreInSecureStore(boolean store) {
		storeInSecureStore = store;
		storeCheckbox.setSelection(store);
	}

	private UserPasswordCredentials getSecureStoreCredentials(
			final URIish finalURI) throws StorageException {
		EGitSecureStore secureStore = org.eclipse.egit.core.Activator
				.getDefault().getSecureStore();
		UserPasswordCredentials credentials = secureStore
				.getCredentials(finalURI);
		return credentials;
	}

