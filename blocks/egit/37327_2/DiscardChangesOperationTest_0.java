	@Test
	public void testDiscardChangesWithPath() throws Exception {
		IFile file1 = project.getFile(new Path("folder1/file1.txt"));
		setNewFileContent(file1, "changed 1");

		DiscardChangesOperation operation = new DiscardChangesOperation(
				Arrays.asList(file1.getLocation()));
		operation.execute(new NullProgressMonitor());

		assertEquals("Hello world 1",
				testUtils.slurpAndClose(file1.getContents()));
	}

