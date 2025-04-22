		super.setUp();

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		gitDir = new File(root.getLocation().toFile(), Constants.DOT_GIT);

		repository = new FileRepository(gitDir);
		repository.create();
		repository.close();
		repository = Activator.getDefault().getRepositoryCache()
				.lookupRepository(gitDir);

		project = root.getProject(TEST_PROJECT);
		project.create(null);
		project.open(null);

		project.getFolder(TEST_FOLDER2).create(true, true, null);
		IFile testFile2 = project.getFile(TEST_FILE2);
		testFile2.create(new ByteArrayInputStream("content".getBytes()), true,
				null);

		RepositoryMapping mapping = new RepositoryMapping(project, gitDir);

		GitProjectData projectData = new GitProjectData(project);
		projectData.setRepositoryMappings(Collections.singleton(mapping));
		projectData.store();
		GitProjectData.add(project, projectData);

		RepositoryProvider.map(project, GitProvider.class.getName());

		git = new Git(repository);
		git.add().addFilepattern(".").call();
		git.commit().setMessage("Initial commit").call();
