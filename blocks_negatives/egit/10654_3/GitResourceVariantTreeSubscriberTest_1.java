	public void shouldReturnSrcBranchAsBase() throws Exception {
		String fileName = "Main.java";
		File file = testRepo.createFile(iProject, fileName);
		RevCommit commit = testRepo.appendContentAndCommit(iProject, file,
				"class Main {}", "initial commit");
		IFile mainJava = testRepo.getIFile(iProject, file);
		testRepo.createAndCheckoutBranch(Constants.HEAD, Constants.R_HEADS
				+ "test");
		testRepo.appendContentAndCommit(iProject, file, "// test1",
				"secont commit");

