	@After
	public void removeConfiguredRepositories() {
		if (parentRepositoryGitDir != null) {
			Activator.getDefault().getRepositoryUtil()
					.removeDir(parentRepositoryGitDir);
		}
		if (childRepositoryGitDir != null) {
			Activator.getDefault().getRepositoryUtil()
					.removeDir(childRepositoryGitDir);
		}
		childRepository = null;
		parentRepository = null;
		subRepository = null;
	}

