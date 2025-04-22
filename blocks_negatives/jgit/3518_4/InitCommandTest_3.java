	public void testInitRepository() {
		try {
			File directory = createTempDirectory("testInitRepository");
			InitCommand command = new InitCommand();
			command.setDirectory(directory);
			Repository repository = command.call().getRepository();
			addRepoToClose(repository);
			assertNotNull(repository);
		} catch (Exception e) {
			fail(e.getMessage());
		}
