	private AbstractTaskReference adaptFromRevCommit(RevCommit commit) {
		Repository[] repositories = Activator.getDefault().getRepositoryCache().getAllRepositories();
		for (Repository r : repositories) {

			String repoUrl = null;
			String message = null;
			long timestamp = 0;

			try (RevWalk revWalk = new RevWalk(r)) {
				RevCommit revCommit = revWalk.parseCommit(commit);
				repoUrl = getRepoUrl(r);
				message = revCommit.getFullMessage();
				timestamp = (long) revCommit.getCommitTime() * 1000;
			} catch (IOException | RuntimeException e) {
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

			return new LinkedTaskInfo(taskRepositoryUrl, null, null, message, timestamp);
