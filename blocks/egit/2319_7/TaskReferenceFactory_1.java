		if (commit != null)
			return adaptFromRevCommit(commit);

		return null;
	}

	private AbstractTaskReference adaptFromRevCommit(RevCommit commit) {
		Repository[] repositories = Activator.getDefault().getRepositoryCache().getAllRepositories();
		for (Repository r : repositories) {
			RevWalk revWalk = new RevWalk(r);

			String repoUrl = null;
			String message = null;

			try {
				RevCommit revCommit = revWalk.parseCommit(commit);
				if (revCommit != null) {
					repoUrl = getRepoUrl(r);
					message = revCommit.getFullMessage();
				}
			} catch (Exception e) {
				continue;
			}

			if (message == null || message.trim().length() == 0)
				continue;

			String taskRepositoryUrl = null;
			if (repoUrl != null) {
				TaskRepository repository = getTaskRepositoryByGitRepoURL(repoUrl);
				if (repository != null)
					taskRepositoryUrl = repository.getRepositoryUrl();
			}

			return new LinkedTaskInfo(taskRepositoryUrl, null, null, message);
		}

		return null;
