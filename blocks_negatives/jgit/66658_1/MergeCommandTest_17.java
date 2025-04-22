		Git git = new Git(db);

		File fileA = writeTrashFile("a", "a");
		RevCommit initialCommit = addAllAndCommit(git);

		createBranch(initialCommit, "refs/heads/side");
		checkoutBranch("refs/heads/side");
		write(fileA, "a(side)");
		writeTrashFile("b", "b");
		RevCommit sideCommit = addAllAndCommit(git);

		checkoutBranch("refs/heads/master");
		writeTrashFile("c", "c");
		addAllAndCommit(git);

		write(fileA, "a(modified)");
		git.add().addFilepattern("a").call();

		String indexState = indexState(CONTENT);

		MergeResult result = git.merge().include(sideCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();

		checkMergeFailedResult(result, MergeFailureReason.DIRTY_INDEX,
				indexState, fileA);
