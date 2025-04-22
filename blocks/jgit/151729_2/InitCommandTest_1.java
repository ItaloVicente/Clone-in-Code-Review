	public void testInitWithDefaultsBare() throws JGitInternalException {
		assertThrows(NoWorkTreeException.class
			MockSystemReader reader = (MockSystemReader) SystemReader
					.getInstance();
			reader.setProperty(Constants.OS_USER_DIR
					getTemporaryDirectory().getAbsolutePath());
			InitCommand command = new InitCommand();
			command.setBare(true);
			try (Git git = command.call()) {
				Repository repository = git.getRepository();
				assertNotNull(repository);
				assertEqualsFile(new File(reader.getProperty("user.dir"))
						repository.getDirectory());
				assertNull(repository.getWorkTree());
			}
		});
