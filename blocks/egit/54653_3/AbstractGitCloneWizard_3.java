		CredentialsProvider credentialsProvider = null;
		if (credentials != null) {
			credentialsProvider = new EGitCredentialsProvider(
					credentials.getUser(), credentials.getPassword());
		} else {
			credentialsProvider = new EGitCredentialsProvider();
		}
		op.setCredentialsProvider(credentialsProvider);
