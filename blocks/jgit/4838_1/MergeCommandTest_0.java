	@Test
	public void testFileModeMerge() throws Exception {
		if (!FS.DETECTED.supportsExecute())
			return;
		Git git = new Git(db);

		writeTrashFile("mergeableMode"
		setExecutable(git
		writeTrashFile("conflictingModeWithBase"
		setExecutable(git
		RevCommit initialCommit = addAllAndCommit(git);

		createBranch(initialCommit
		checkoutBranch("refs/heads/side");
		setExecutable(git
		writeTrashFile("conflictingModeNoBase"
		setExecutable(git
		RevCommit sideCommit = addAllAndCommit(git);

		createBranch(initialCommit
		checkoutBranch("refs/heads/side2");
		setExecutable(git
		assertFalse(new File(git.getRepository().getWorkTree()
				"conflictingModeNoBase").exists());
		writeTrashFile("conflictingModeNoBase"
		setExecutable(git
		addAllAndCommit(git);

		MergeResult result = git.merge().include(sideCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.CONFLICTING
		assertTrue(canExecute(git
		assertFalse(canExecute(git
	}

	@Test
	public void testFileModeMergeWithDirtyWorkTree() throws Exception {
		if (!FS.DETECTED.supportsExecute())
			return;

		Git git = new Git(db);

		writeTrashFile("mergeableButDirty"
		setExecutable(git
		RevCommit initialCommit = addAllAndCommit(git);

		createBranch(initialCommit
		checkoutBranch("refs/heads/side");
		setExecutable(git
		RevCommit sideCommit = addAllAndCommit(git);

		createBranch(initialCommit
		checkoutBranch("refs/heads/side2");
		setExecutable(git
		addAllAndCommit(git);

		writeTrashFile("mergeableButDirty"

		MergeResult result = git.merge().include(sideCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.FAILED
		assertFalse(canExecute(git
	}

	private void setExecutable(Git git
		FS.DETECTED.setExecute(
				new File(git.getRepository().getWorkTree()
	}

	private boolean canExecute(Git git
		return FS.DETECTED.canExecute(new File(git.getRepository()
				.getWorkTree()
	}

