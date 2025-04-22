	@Override
	protected RepositorySelection getRepositorySelection() {
		return cloneSource.getSelection();
	}

	@Override
	protected UserPasswordCredentials getCredentials() {
		return cloneSource.getCredentials();
