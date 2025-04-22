	@Ignore
	public void shouldReturnCommonAncestorAsBase() throws Exception {
		String fileName = "Main.java";
		File file = testRepo.createFile(iProject, fileName);
		RevCommit commit = testRepo.appendContentAndCommit(iProject, file,
				"class Main {}", "initial commit");
		IFile mainJava = testRepo.getIFile(iProject, file);
		ObjectId fileId = findFileId(commit, mainJava);

		testRepo.createAndCheckoutBranch(Constants.HEAD, Constants.R_HEADS
				+ "test");
		testRepo.appendContentAndCommit(iProject, file, "// test 1",
				"second commit");

		testRepo.checkoutBranch(Constants.R_HEADS + Constants.MASTER);
		testRepo.appendContentAndCommit(iProject, file, "// test 2",
				"third commit");

