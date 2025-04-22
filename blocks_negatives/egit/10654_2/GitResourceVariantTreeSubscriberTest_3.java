	@Ignore
	public void shouldReturnRemoteTree() throws Exception {
		String fileName = "Main.java";
		File file = testRepo.createFile(iProject, fileName);
		testRepo.appendContentAndCommit(iProject, file,
				"class Main {}", "initial commit");
		IFile mainJava = testRepo.getIFile(iProject, file);
