		Git git = new Git(db);
		RevCommit initialCommit = git.commit().setMessage("initial commit")
				.call();
		createBranch(initialCommit, "refs/heads/branch1");
		RevCommit secondCommit = git.commit().setMessage("second commit")
				.call();
		checkoutBranch("refs/heads/branch1");

		MergeCommand merge = git.merge();
		merge.setFastForward(FastForwardMode.NO_FF);
		merge.include(db.exactRef(R_HEADS + MASTER));
		merge.setCommit(false);
		MergeResult result = merge.call();

		assertEquals(MergeStatus.MERGED_NOT_COMMITTED, result.getMergeStatus());
		assertEquals(2, result.getMergedCommits().length);
		assertEquals(initialCommit, result.getMergedCommits()[0]);
		assertEquals(secondCommit, result.getMergedCommits()[1]);
		assertNull(result.getNewHead());
		assertEquals(RepositoryState.MERGING_RESOLVED, db.getRepositoryState());
