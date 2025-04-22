		Git git = new Git(db);

		writeTrashFile("mergeableMode", "a");
		setExecutable(git, "mergeableMode", false);
		writeTrashFile("conflictingModeWithBase", "a");
		setExecutable(git, "conflictingModeWithBase", false);
		RevCommit initialCommit = addAllAndCommit(git);

		createBranch(initialCommit, "refs/heads/side");
		checkoutBranch("refs/heads/side");
		setExecutable(git, "mergeableMode", true);
		writeTrashFile("conflictingModeNoBase", "b");
		setExecutable(git, "conflictingModeNoBase", true);
		RevCommit sideCommit = addAllAndCommit(git);

		createBranch(initialCommit, "refs/heads/side2");
		checkoutBranch("refs/heads/side2");
		setExecutable(git, "mergeableMode", false);
		assertFalse(new File(git.getRepository().getWorkTree(),
				"conflictingModeNoBase").exists());
		writeTrashFile("conflictingModeNoBase", "b");
		setExecutable(git, "conflictingModeNoBase", false);
		addAllAndCommit(git);

		MergeResult result = git.merge().include(sideCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.CONFLICTING, result.getMergeStatus());
		assertTrue(canExecute(git, "mergeableMode"));
		assertFalse(canExecute(git, "conflictingModeNoBase"));
