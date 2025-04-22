		try {
			Activator.getDefault().getSecureStore().clearCredentials(uri);
			user = null;
			password = null;
		} catch (IOException e) {
			Activator.logError(MessageFormat.format(
					UIText.EGitCredentialsProvider_FailedToClearCredentials,
					uri), e);
		}
