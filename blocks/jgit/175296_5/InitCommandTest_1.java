			Repository r = git.getRepository();
			assertNotNull(r);
			assertEquals("refs/heads/master"
		}
	}

	@Test
	public void testInitRepositoryMainInitialBranch()
			throws IOException
		File directory = createTempDirectory("testInitRepository");
		InitCommand command = new InitCommand();
		command.setDirectory(directory);
		command.setInitialBranch("main");
		try (Git git = command.call()) {
			Repository r = git.getRepository();
			assertNotNull(r);
			assertEquals("refs/heads/main"
