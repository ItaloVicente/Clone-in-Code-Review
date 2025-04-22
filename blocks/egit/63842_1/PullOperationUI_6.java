	public PullOperationUI(Map<Repository, PullReferenceConfig> pulls) {
		this.repositories = pulls.keySet()
				.toArray(new Repository[pulls.size()]);
		int timeout = Activator.getDefault().getPreferenceStore()
				.getInt(UIPreferences.REMOTE_CONNECTION_TIMEOUT);
		pullOperation = new PullOperation(pulls, timeout);
		pullOperation.setCredentialsProvider(new EGitCredentialsProvider());
		for (Repository repository : repositories) {
			results.put(repository, NOT_TRIED_STATUS);
		}
	}

