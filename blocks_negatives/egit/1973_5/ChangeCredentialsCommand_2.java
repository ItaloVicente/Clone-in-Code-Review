		UserPasswordCredentials credentials = LoginDialog.changeCredentials(getShell(event), uri);
		if (credentials == null)
			return null;
		try {
			Activator.getDefault().getSecureStore()
					.putCredentials(uri, credentials);
		} catch (StorageException e) {
			Activator.error(
					UIText.ChangeCredentialsCommand_writingToSecureStoreFailed,
					e);
		} catch (IOException e) {
			Activator.error(
					UIText.ChangeCredentialsCommand_writingToSecureStoreFailed,
					e);
		}
