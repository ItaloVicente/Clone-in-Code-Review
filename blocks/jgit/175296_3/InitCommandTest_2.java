			assertEquals("refs/heads/master"
		}
	}

	@Test
	public void testInitBareRepositoryMainInitialBranch()
			throws IOException
		File directory = createTempDirectory("testInitBareRepository");
		InitCommand command = new InitCommand();
		command.setDirectory(directory);
		command.setBare(true);
		command.setInitialBranch("main");
		try (Git git = command.call()) {
			Repository repository = git.getRepository();
			assertNotNull(repository);
			assertTrue(repository.isBare());
			assertEquals("refs/heads/main"
