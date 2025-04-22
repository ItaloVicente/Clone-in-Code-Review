
	@Test
	public void testInitWithExplicitGitDir() throws IOException
			JGitInternalException
		File wt = createTempDirectory("testInitRepositoryWT");
		File gitDir = createTempDirectory("testInitRepositoryGIT");
		InitCommand command = new InitCommand();
		command.setDirectory(wt);
		command.setSeparateGitDir(gitDir);
		Repository repository = command.call().getRepository();
		addRepoToClose(repository);
		assertNotNull(repository);
		assertEqualsFile(wt
		assertEqualsFile(gitDir
	}

	@Ignore("Test would create/delete files in current dir")
	@Test
	public void testInitWithOnlyExplicitGitDir() throws IOException
			JGitInternalException
		File gitDir = createTempDirectory("testInitRepository/.git");
		InitCommand command = new InitCommand();
		command.setSeparateGitDir(gitDir);
		Repository repository = command.call().getRepository();
		addRepoToClose(repository);
		assertNotNull(repository);
		assertEqualsFile(gitDir
		assertEqualsFile(new File(".")
	}

	@Test(expected = IllegalStateException.class)
	public void testInitBare_DirAndGitDirMustBeEqual() throws IOException
			JGitInternalException
		File gitDir = createTempDirectory("testInitRepository.git");
		InitCommand command = new InitCommand();
		command.setBare(true);
		command.setDirectory(gitDir);
		command.setSeparateGitDir(new File(gitDir
		command.call();
	}

	@Ignore("Test would create/delete files in current dir")
	@Test
	public void testInitWithDefaultsNonBare() throws JGitInternalException
			GitAPIException
		InitCommand command = new InitCommand();
		command.setBare(false);
		Repository repository = command.call().getRepository();
		addRepoToClose(repository);
		assertNotNull(repository);
		assertEqualsFile(new File(".git")
		assertEqualsFile(new File(".")
	}

	@Ignore("Test would create/delete files in current dir")
	@Test(expected = NoWorkTreeException.class)
	public void testInitWithDefaultsBare() throws JGitInternalException
			GitAPIException
		InitCommand command = new InitCommand();
		command.setBare(true);
		Repository repository = command.call().getRepository();
		addRepoToClose(repository);
		assertNotNull(repository);
		assertEqualsFile(new File(".")
		assertNull(repository.getWorkTree());
	}

	@Test(expected = IllegalStateException.class)
	public void testInitNonBare_GitdirAndDirShouldntBeSame()
			throws JGitInternalException
		File gitDir = createTempDirectory("testInitRepository.git");
		InitCommand command = new InitCommand();
		command.setBare(false);
		command.setSeparateGitDir(gitDir);
		command.setDirectory(gitDir);
		command.call().getRepository();
	}
