	@Test
	public void hasRemoteChangeInNewFolder() throws Exception {
		File file1 = testRepo.createFile(iProject, "file1.sample");
		String initialContent1 = "some content for the first file";
		testRepo.appendContentAndCommit(iProject, file1, initialContent1,
				"first file - initial commit");
