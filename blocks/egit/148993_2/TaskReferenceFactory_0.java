		TaskRepository repository = getTaskRepositoryByGitRepoURL(repoUrl);
		if (repository == null) {
			return null;
		}
		String taskRepositoryUrl = repository.getRepositoryUrl();

		String message = null;
		long timestamp = 0;
		try (RevWalk revWalk = new RevWalk(r)) {
			RevCommit revCommit = revWalk.parseCommit(commit.getRevCommit());
			message = revCommit.getFullMessage();
			timestamp = (long) revCommit.getCommitTime() * 1000;
		} catch (IOException | RuntimeException e) {
			return null;
		}
		if (message == null || message.trim().isEmpty()) {
			return null;
		}
		return new LinkedTaskInfo(taskRepositoryUrl, null, null, message,
				timestamp);
