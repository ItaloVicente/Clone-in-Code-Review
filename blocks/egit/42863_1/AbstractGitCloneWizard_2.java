	private void configureGerrit(CloneOperation op,
			GitRepositoryInfo gitRepositoryInfo, String remoteName, int timeout) {
		ConfigureGerritAfterCloneTask task = new ConfigureGerritAfterCloneTask(
				gitRepositoryInfo.getCloneUri(), remoteName, timeout);
		op.addPostCloneTask(task);
	}

