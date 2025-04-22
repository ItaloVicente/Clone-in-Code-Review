	static Git cloneSubdirectory(final File repoDest
			final List<String> branches
			final File hookDir) throws IOException {
		return new SubdirectoryClone(repoDest
				JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY).execute();
	}
