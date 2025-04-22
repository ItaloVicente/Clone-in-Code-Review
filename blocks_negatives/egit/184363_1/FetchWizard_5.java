
	private String getSourceString() {
		final RepositorySelection repoSelection = repoPage.getSelection();
		if (repoSelection.isConfigSelected())
			return repoSelection.getConfigName();
		return repoSelection.getURI(false).toString();
	}
