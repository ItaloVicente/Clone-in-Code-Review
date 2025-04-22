		fileA.create(new ByteArrayInputStream(srcA.getBytes("UTF-8")), false,
				null);

		TestRepository thisGit = new TestRepository(gitDir);

		File committable = new File(fileA.getLocationURI());

		thisGit.addAndCommit(project.project, committable,
				"testNewUnsharedFile\n\nJunit tests\n");

		assertNull(RepositoryProvider.getProvider(project.getProject()));
