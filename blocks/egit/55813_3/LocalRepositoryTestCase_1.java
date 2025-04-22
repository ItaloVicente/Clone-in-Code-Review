	protected Repository createLocalTestRepository(String repoName)
			throws IOException {
		File gitDir = new File(new File(testDirectory, repoName),
				Constants.DOT_GIT);
		Repository myRepository = new RepositoryBuilder().setGitDir(gitDir)
				.build();
		myRepository.create();
		return myRepository;
	}

	protected IProject createStandardTestProjectInRepository(
			Repository repository, String name) throws Exception {
		IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(name);

		if (project.exists()) {
			project.delete(true, null);
			TestUtil.waitForJobs(100, 5000);
		}
		IProjectDescription desc = ResourcesPlugin.getWorkspace()
				.newProjectDescription(name);
		desc.setLocation(
				new Path(new File(repository.getWorkTree(), name).getPath()));
		project.create(desc, null);
		project.open(null);
		TestUtil.waitForJobs(50, 5000);

		assertTrue("Project is not accessible: " + project,
				project.isAccessible());

		IFolder folder = project.getFolder(FOLDER);
		folder.create(false, true, null);
		IFile textFile = folder.getFile(FILE1);
		textFile.create(
				new ByteArrayInputStream(
						"Hello, world".getBytes(project.getDefaultCharset())),
				false, null);
		IFile textFile2 = folder.getFile(FILE2);
		textFile2.create(new ByteArrayInputStream(
				"Some more content".getBytes(project.getDefaultCharset())),
				false, null);
		return project;
	}

