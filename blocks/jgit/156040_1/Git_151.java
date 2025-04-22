	static Git fork(final File gitRepoContainerDir
			final CredentialsProvider credential
			throws IOException {
		return new Fork(gitRepoContainerDir
				JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY).execute();
	}
