	@Test
	public void test559539() throws ExecutionException, CoreException {
		IFile file = getWorkspaceRoot().getFile(testProject.getFullPath().append(TEST_NEWFILE_NAME));
		CreateFileOperation op = new CreateFileOperation(file, null, getContents(
				getRandomString()),
				"testFileCreateLeaf");
		execute(op);
		assertTrue("Operation should be valid", op.canUndo());
		file.delete(true, getMonitor());

		for (int i = 0; i < NUM_CHANGES; i++) {
			IMarker marker = emptyTestFile.createMarker(IMarker.BOOKMARK);
			marker.setAttributes(initialAttributes);
			marker.delete();
		}

		assertTrue("Operation should be valid", op.canUndo());
	}

