	@Test
	public void testInitNonEmptyRepository() {
		try {
			File directory = createTempDirectory("testInitRepository2");
			File someFile = new File(directory
			someFile.createNewFile();
			assertTrue(someFile.exists());
			assertTrue(directory.listFiles().length > 0);
			InitCommand command = new InitCommand();
			command.setDirectory(directory);
			Repository repository = command.call().getRepository();
			addRepoToClose(repository);
			assertNotNull(repository);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

