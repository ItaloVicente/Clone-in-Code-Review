		String fileName = "Main.java";
		File file = testRepo.createFile(iProject, fileName);
		testRepo.appendContentAndCommit(iProject, file, "class Main {}",
				"initial commit");
		IFile mainJava = testRepo.getIFile(iProject, file);

		testRepo.createAndCheckoutBranch(Constants.R_HEADS + Constants.MASTER, Constants.R_HEADS + "test");
		testRepo.appendContentAndCommit(iProject, file, "// test", "first commit");
