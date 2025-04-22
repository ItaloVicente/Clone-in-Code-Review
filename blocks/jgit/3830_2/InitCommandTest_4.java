	public void testInitBareRepository() throws IOException {
		File directory = createTempDirectory("testInitBareRepository");
		InitCommand command = new InitCommand();
		command.setDirectory(directory);
		command.setBare(true);
		Repository repository = command.call().getRepository();
		addRepoToClose(repository);
		assertNotNull(repository);
		assertTrue(repository.isBare());
