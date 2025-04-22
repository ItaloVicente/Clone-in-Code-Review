	public PullOperationUI(Set<Repository> repositories) {
		this.repositories = repositories.toArray(new Repository[repositories
				.size()]);
		int timeout = Activator.getDefault().getPreferenceStore().getInt(
				UIPreferences.REMOTE_CONNECTION_TIMEOUT);
		pullOperation = new PullOperation(repositories, timeout);
		for (Repository repository : repositories)
			results.put(repository, NOT_TRIED_STATUS);
