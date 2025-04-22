
	@Test
	public void findsRepositoryForOpenProject() throws Exception {
		IFile a = testUtils.addFileToProject(project.getProject(),
				"folder1/a.txt", "a");
		assertEquals(repository, cache.getRepository(a));
	}

	@Test
	public void findsRepositoryForClosedProject() throws Exception {
		IFile a = testUtils.addFileToProject(project.getProject(),
				"folder1/a.txt", "a");
		project.getProject().close(null);
		assertEquals(repository, cache.getRepository(a));
	}

	@Test
	public void findsNestedRepositoryForClosedProject()
			throws Exception {
		IFile a = testUtils.addFileToProject(project.getProject(),
				"folder1/a.txt", "a");

		File workdir = project.createFolder("nested").getLocation().toFile();
		TestRepository repository2 = new TestRepository(new File(workdir,
				Constants.DOT_GIT));

		String projectName = "project2";
		IProject project2 = testUtils.createProjectInLocalFileSystem(workdir,
				projectName);
		IFile b = testUtils.addFileToProject(project2, "folder1/b.txt",
				"Hello world");
		repository2.connect(project2);

		project.getProject().close(null);
		project2.getProject().close(null);

		assertEquals(repository, cache.getRepository(a));
		assertEquals(repository2.repository, cache.getRepository(b));
	}
