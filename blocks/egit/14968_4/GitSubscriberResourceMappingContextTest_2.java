		IFile iFile1 = testRepo.getIFile(iProject, file1);

		testRepo.createAndCheckoutBranch(MASTER, BRANCH);

		File file2 = testRepo.createFile(iProject, "file2.sample");
		String initialContent2 = "some content for the second file";
