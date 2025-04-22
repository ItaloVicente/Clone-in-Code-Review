					if (useSecureStore) {
						UserPasswordCredentials credentials = new UserPasswordCredentials(
								"egit:ssh:resource", new String(password)); //$NON-NLS-1$
						try {
							store.putCredentials(uri, credentials);
						} catch (StorageException e) {
							if (e.getErrorCode() == StorageException.NO_PASSWORD) {
								useSecureStore = false;
								savePrefs();
							} else {
								Activator.logError(e.getMessage(), e);
							}
						} catch (RuntimeException e) {
							Activator.logError(e.getMessage(), e);
						}
