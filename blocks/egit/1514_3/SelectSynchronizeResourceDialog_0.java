	private String getDefaultRemoteName() {
		boolean onlyOneRemote = syncRepos.size() == 2;
		if (onlyOneRemote)
			return syncRepos.get(1).getName();
		else {
			for (SyncRepoEntity repo : syncRepos)
				if (repo.getName().equals(DEFAULT_REMOTE_NAME))
					return DEFAULT_REMOTE_NAME;
		}
		return ""; //$NON-NLS-1$
	}

