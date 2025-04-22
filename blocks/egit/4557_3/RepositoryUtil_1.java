	public boolean contains(final Repository repository) {
		return contains(getPath(repository.getDirectory()));
	}

	public boolean contains(final String repositoryDir) {
		return getConfiguredRepositories().contains(repositoryDir);
	}
