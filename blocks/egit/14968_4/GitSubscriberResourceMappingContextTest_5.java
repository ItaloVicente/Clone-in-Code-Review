
		testRepo.createAndCheckoutBranch(MASTER, BRANCH);

		iProject.getFolder("folder").create(true, true,
				new NullProgressMonitor());
		File file2 = testRepo.createFile(iProject, "folder/file2.sample");
		String initialContent2 = "some content for the second file";
		testRepo.appendContentAndCommit(iProject, file2, initialContent2,
				"second file - initial commit");
