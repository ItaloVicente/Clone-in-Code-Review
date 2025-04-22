	@Test
	public void testIgnoreWithResource() throws Exception {
		IFolder binFolder = project.getProject().getFolder("bin");
		@SuppressWarnings("deprecation")
		IgnoreOperation operation = new IgnoreOperation(new IResource[] {binFolder});
		operation.execute(new NullProgressMonitor());

		String content = project.getFileContent(Constants.GITIGNORE_FILENAME);
		assertEquals("/bin\n", content);
	}

