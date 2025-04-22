		testRepo.connect(project.getProject());

		String fileName = "Main.java";
		File file = testRepo.createFile(project.getProject(), fileName);
		initialCommit = testRepo.appendContentAndCommit(project.getProject(),
				file, "class Main {}", "initial commit");
		changedFile = testRepo.getIFile(project.getProject(), file);

		testRepo.createAndCheckoutBranch(Constants.HEAD, Constants.R_HEADS
				+ "test");
		commitBranch = testRepo.appendContentAndCommit(project.getProject(),
				file, "// test 1", "second commit");

		testRepo.checkoutBranch(Constants.R_HEADS + Constants.MASTER);
		commitMaster = testRepo.appendContentAndCommit(project.getProject(),
				file, "// test 2", "third commit");
