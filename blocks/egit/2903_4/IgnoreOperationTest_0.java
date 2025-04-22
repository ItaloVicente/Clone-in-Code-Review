	@Test
	public void testIgnoreNoTrailingNewline() throws Exception {
		String existing = "/nonewline";
		IFile ignore = project.getProject().getFile(
				Constants.GITIGNORE_FILENAME);
		assertFalse(ignore.exists());
		ignore.create(new ByteArrayInputStream(existing.getBytes()),
				IResource.FORCE, new NullProgressMonitor());

		IFolder binFolder = project.getProject().getFolder("bin");
		IgnoreOperation operation = new IgnoreOperation(
				new IResource[] { binFolder });
		operation.execute(new NullProgressMonitor());

		String content = project.getFileContent(Constants.GITIGNORE_FILENAME);
		assertEquals(existing + "\n/bin\n", content);
		assertFalse(operation.isGitignoreOutsideWSChanged());
	}
