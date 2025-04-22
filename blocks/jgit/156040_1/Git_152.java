	static Git clone(final File repoDest
			final CredentialsProvider credential
			throws IOException {
		return new Clone(repoDest
				JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY).execute().get();
	}
