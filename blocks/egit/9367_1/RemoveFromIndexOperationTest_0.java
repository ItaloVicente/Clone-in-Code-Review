	@Test
	public void shouldRemoveFromIndexOnInitialCommit() throws Exception {
		testRepo.dispose();
		FileUtils.delete(gitDir, FileUtils.RECURSIVE | FileUtils.RETRY);
		testRepo = new TestRepository(gitDir);
		testRepo.connect(project.getProject());

		IFile file = testUtils.addFileToProject(project.getProject(), "file.txt", "content");
		new AddToIndexOperation(asList(file)).execute(null);

		assertTrue(testRepo.inIndex(file.getLocation().toString()));

		new RemoveFromIndexOperation(Arrays.asList(file.getLocation())).execute(null);

		assertFalse(testRepo.inIndex(file.getLocation().toString()));
		assertTrue(file.getLocation().toFile().exists());
	}

