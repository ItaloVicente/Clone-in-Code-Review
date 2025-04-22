	public void testObjectDirectorySnapshot() throws Exception {
		RevCommit[] commits = setupLinearChain();

		createShallowFile(commits[3]);
		updateCommits(commits);

		markStart(commits[3]);
		assertCommit(commits[3]
		assertNull(rw.next());

		createShallowFile(commits[2]);
		updateCommits(commits);

		markStart(commits[3]);
		assertCommit(commits[3]
		assertCommit(commits[2]
