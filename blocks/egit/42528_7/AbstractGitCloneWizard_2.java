	private void configureGerrit(CloneOperation op,
			GitRepositoryInfo gitRepositoryInfo, String remoteName) {
		ConfigureGerritAfterCloneTask task = new ConfigureGerritAfterCloneTask(
				gitRepositoryInfo.getCloneUri(), remoteName);
		op.addPostCloneTask(task);
	}

