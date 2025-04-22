	private boolean storeCredentials() {
		UserPasswordCredentials credentials = cloneSource.getCredentials();
		if (credentials != null) {
			URIish uri = cloneSource.getSelection().getURI();
			try {
				org.eclipse.egit.core.Activator.getDefault().getSecureStore().putCredentials(uri, credentials);
			} catch (StorageException e) {
				Activator
						.handleError(
								UIText.GitCloneWizard_writeToSecureStoreFailed,
								e, true);
				return false;
			} catch (IOException e) {
				Activator
						.handleError(
								UIText.GitCloneWizard_writeToSecureStoreFailed,
								e, true);
				return false;
			}
		}
		return true;
	}

