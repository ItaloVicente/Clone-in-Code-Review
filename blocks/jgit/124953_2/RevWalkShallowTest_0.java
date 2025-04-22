		RevCommit[] commits = setupLinearChain();

		createShallowFile(commits[1]);
		reparseCommits(commits);

		rw.markStart(commits[3]);
		assertEquals(commits[3]
		assertEquals(commits[2]
		assertEquals(commits[1]
