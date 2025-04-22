	private String getDefaultRemoteName() {
		String defaultName = ""; //$NON-NLS-1$
		boolean onlyOneRemote = syncRepos.size() == 2;
		if (onlyOneRemote) {
			defaultName = syncRepos.get(1).getName();
		} else {
			for (SyncRepoEntity repo : syncRepos)
				if (repo.getName().equals(DEFAULT_REMOTE_NAME))
					return DEFAULT_REMOTE_NAME;
		}
		return defaultName;
	}

