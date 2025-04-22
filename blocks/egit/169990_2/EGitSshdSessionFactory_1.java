				if (useSecureStore) {
					try {
						UserPasswordCredentials credentials = store
								.getCredentials(uri);
						if (credentials != null) {
							String password = credentials.getPassword();
							if (password != null) {
								char[] pass = password.toCharArray();
								state.setPassword(pass);
								return pass;
							}
						}
					} catch (StorageException e) {
						if (e.getErrorCode() == StorageException.NO_PASSWORD) {
							useSecureStore = false;
							savePrefs();
						} else {
							Activator.logError(e.getMessage(), e);
