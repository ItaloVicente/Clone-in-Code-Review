		assertEquals("/bin/\n", content);
		assertFalse(operation.isGitignoreOutsideWSChanged());
	}

	@Test
	public void testIgnoreFile() throws Exception {
		IFile aFile = project.createFile("aFile.txt", new byte[0]);
		IgnoreOperation operation = executeIgnore(aFile.getLocation());
		String content = project.getFileContent(Constants.GITIGNORE_FILENAME);
		assertEquals("/aFile.txt\n", content);
