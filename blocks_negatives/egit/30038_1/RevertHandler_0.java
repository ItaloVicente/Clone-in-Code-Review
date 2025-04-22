		List<RepositoryCommit> commits = getCommits(event);
		if (commits.size() == 1) {
			RepositoryCommit repoCommit = commits.get(0);
			final RevCommit commit = repoCommit.getRevCommit();
			Repository repo = repoCommit.getRepository();
			final Shell shell = getPart(event).getSite().getShell();
