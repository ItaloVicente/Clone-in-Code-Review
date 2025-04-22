	private static CredentialsProvider getCredentialsProvider(
			ClientSession session) {
		if (session instanceof JGitClientSession) {
			return ((JGitClientSession) session).getCredentialsProvider();
		}
		return null;
	}
