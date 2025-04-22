	@Nullable
	public String getGitwebDescription() throws IOException {
		return null;
	}

	public void setGitwebDescription(@Nullable String description)
			throws IOException {
		throw new IOException(JGitText.get().unsupportedRepositoryDescription);
	}

