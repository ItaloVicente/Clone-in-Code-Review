
	@Test
	public void testAddFilesInFolderWithDerivedFile() throws Exception {
		IFile file1 = testUtils.addFileToProject(project.getProject(),
				"sub/a.txt", "some text");
		IFile file2 = testUtils.addFileToProject(project.getProject(),
				"sub/b.txt", "some text");
		file2.setDerived(true);

		resources.add(project.getProject().getFolder("sub"));
		new AddToIndexOperation(resources).execute(null);

		assertTrue(testRepository.inIndex(file1.getLocation()
				.toPortableString()));
		assertFalse(testRepository.inIndex(file2.getLocation()
				.toPortableString()));
	}

	@Test
	public void testAddWholeProject() throws Exception {
		IFile file1 = testUtils.addFileToProject(project.getProject(),
				"sub/a.txt", "some text");
		IFile file2 = testUtils.addFileToProject(project.getProject(),
				"sub/b.txt", "some text");
		file2.setDerived(true);

		resources.add(project.getProject());
		new AddToIndexOperation(resources).execute(null);

		assertTrue(testRepository.inIndex(file1.getLocation()
				.toPortableString()));
		assertFalse(testRepository.inIndex(file2.getLocation()
				.toPortableString()));
	}

