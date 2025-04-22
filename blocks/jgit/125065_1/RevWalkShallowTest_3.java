	@Test
	public void testMergeCommitOneParentShallow() throws Exception {
		RevCommit[] commits = setupMergeChain();

		createShallowFile(commits[4]);
		updateCommits(commits);

		markStart(commits[5]);
		assertCommit(commits[5]
		assertCommit(commits[4]
		assertCommit(commits[2]
		assertCommit(commits[1]
		assertCommit(commits[0]
