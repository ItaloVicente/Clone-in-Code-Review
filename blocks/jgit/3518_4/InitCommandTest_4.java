	public void testInitRepository() throws IOException {
		File directory = createTempDirectory("testInitRepository");
		InitCommand command = new InitCommand();
		command.setDirectory(directory);
		Repository repository = command.call().getRepository();
		addRepoToClose(repository);
		assertNotNull(repository);
