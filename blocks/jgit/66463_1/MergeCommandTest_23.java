		try (Git git = new Git(db)) {
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
