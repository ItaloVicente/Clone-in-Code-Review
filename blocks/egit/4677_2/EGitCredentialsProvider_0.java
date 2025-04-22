	@Override
	public void reset(URIish uri) {
		try {
			Activator.getDefault().getSecureStore().clearCredentials(uri);
		} catch (IOException e) {
			Activator.logError(MessageFormat.format(
					UIText.EGitCredentialsProvider_FailedToClearCredentials,
					uri), e);
		}
	}

