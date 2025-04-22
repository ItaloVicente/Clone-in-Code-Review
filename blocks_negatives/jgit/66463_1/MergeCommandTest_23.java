		Git git = new Git(db);

		writeTrashFile("mergeableButDirty", "a");
		setExecutable(git, "mergeableButDirty", false);
		RevCommit initialCommit = addAllAndCommit(git);

		createBranch(initialCommit, "refs/heads/side");
		checkoutBranch("refs/heads/side");
		setExecutable(git, "mergeableButDirty", true);
		RevCommit sideCommit = addAllAndCommit(git);

		createBranch(initialCommit, "refs/heads/side2");
		checkoutBranch("refs/heads/side2");
		setExecutable(git, "mergeableButDirty", false);
		addAllAndCommit(git);

		writeTrashFile("mergeableButDirty", "b");

		MergeResult result = git.merge().include(sideCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.FAILED, result.getMergeStatus());
		assertFalse(canExecute(git, "mergeableButDirty"));
