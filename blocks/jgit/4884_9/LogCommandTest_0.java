		return commits;
	}

	@Test
	public void logAllCommitsWithMaxCount() throws Exception {
		Git git = Git.wrap(db);
		List<RevCommit> commits = createCommits(git);
