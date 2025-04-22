	@Nullable
	public String getDescription() throws IOException {
		return null;
	}

	public void setDescription(@Nullable String description)
			throws IOException {
		throw new IOException(JGitText.get().unsupportedRepositoryDescription);
	}

