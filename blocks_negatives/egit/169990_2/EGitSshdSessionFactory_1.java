					UserPasswordCredentials credentials = new UserPasswordCredentials(
							"egit:ssh:resource", new String(password)); //$NON-NLS-1$
					try {
						store.putCredentials(uri, credentials);
					} catch (StorageException | RuntimeException e) {
						Activator.logError(e.getMessage(), e);
