	@After
	public void removeConfiguredRepositories() {
		Activator.getDefault().getRepositoryUtil()
				.removeDir(parentRepositoryGitDir);
		Activator.getDefault().getRepositoryUtil()
				.removeDir(childRepositoryGitDir);
	}

