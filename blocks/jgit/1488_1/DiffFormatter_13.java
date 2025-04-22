	private void assertHaveRepository() {
		if (db == null)
			throw new IllegalStateException(JGitText.get().repositoryIsRequired);
	}

	private byte[] open(String path
			throws IOException {
