	@Test
	public void shouldUnstageFilesFromMultipleRepositories() throws Exception {
		IFile fileRepo1 = testUtils.addFileToProject(project.getProject(), "1.txt", "content");
		IFile fileRepo2 = testUtils.addFileToProject(project2.getProject(), "2.txt", "content");
		new AddToIndexOperation(asList(fileRepo1)).execute(null);
		new AddToIndexOperation(asList(fileRepo2)).execute(null);

		new RemoveFromIndexOperation(Arrays.asList(fileRepo1.getLocation(), fileRepo2.getLocation())).execute(null);

		assertTrue(testRepo.removedFromIndex(fileRepo1.getLocation()
				.toPortableString()));
		assertTrue(testRepo.removedFromIndex(fileRepo2.getLocation()
				.toPortableString()));
	}

