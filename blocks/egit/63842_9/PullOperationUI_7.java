	public PullOperationUI(Map<Repository, PullReferenceConfig> configs) {
		this.repositories = configs.keySet()
				.toArray(new Repository[configs.size()]);
		int timeout = Activator.getDefault().getPreferenceStore()
				.getInt(UIPreferences.REMOTE_CONNECTION_TIMEOUT);
		pullOperation = new PullOperation(configs, timeout);
		pullOperation.setCredentialsProvider(new EGitCredentialsProvider());
		for (Repository repository : repositories) {
			results.put(repository, NOT_TRIED_STATUS);
		}
	}

