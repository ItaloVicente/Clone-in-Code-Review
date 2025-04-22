	private GitResourceVariantTreeProvider createTreeProviderWithChangeDeleteConflicts()
			throws Exception {
		file1 = testRepo.createFile(iProject, "file1");
		testRepo.appendContentAndCommit(iProject, file1, INITIAL_CONTENT_1,
				"Creation of file1 in master.");
		file2 = testRepo.createFile(iProject, "file2");
		testRepo.appendContentAndCommit(iProject, file2, INITIAL_CONTENT_2,
				"Creation of file2 in master.");
		testRepo.createBranch(MASTER, BASE);

		testRepo.createAndCheckoutBranch(MASTER, BRANCH);
		testRepo.untrack(file1);
		testRepo.appendContentAndCommit(iProject, file2, BRANCH_CHANGES,
				"Modified file2 in branch.");
		testRepo.commit("Removed file1 in branch.");

		testRepo.checkoutBranch(MASTER);
		testRepo.untrack(file2);
		testRepo.appendContentAndCommit(iProject, file1, MASTER_CHANGES,
				"Modified file1 in master.");

		iProject.refreshLocal(IResource.DEPTH_INFINITE,
				new NullProgressMonitor());

		try (RevWalk walk = new RevWalk(repo)) {
			RevTree baseTree = walk.parseTree(repo.resolve(BASE));
			RevTree sourceTree = walk.parseTree(repo.resolve(MASTER));
			RevTree remoteTree = walk.parseTree(repo.resolve(BRANCH));
			TreeWalk tw = new TreeWalk(repo);
			tw.addTree(baseTree);
			tw.addTree(sourceTree);
			tw.addTree(remoteTree);
			return new TreeWalkResourceVariantTreeProvider(repo, tw, 0, 1, 2);
		}
	}

