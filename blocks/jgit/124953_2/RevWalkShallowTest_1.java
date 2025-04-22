	public void testObjectDirectorySnapshot() throws Exception {
		RevCommit[] commits = setupLinearChain();

		createShallowFile(commits[3]);
		reparseCommits(commits);

		markStart(commits[3]);
		assertCommit(commits[3]
