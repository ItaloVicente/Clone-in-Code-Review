		Git git = new Git(db);
		RevCommit initialCommit = git.commit().setMessage("initial commit")
				.call();
		createBranch(initialCommit, "refs/heads/branch1");
		git.commit().setMessage("second commit").call();
		checkoutBranch("refs/heads/branch1");

		MergeCommand merge = git.merge();
		merge.setFastForward(FastForwardMode.NO_FF);
		merge.include(db.exactRef(R_HEADS + MASTER));
		MergeResult result = merge.call();

		assertEquals(MergeStatus.MERGED, result.getMergeStatus());
