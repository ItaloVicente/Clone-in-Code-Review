	@Test
	void testInitNonBare_GitdirAndDirShouldntBeSame() {
		assertThrows(IllegalStateException.class
			File gitDir = createTempDirectory("testInitRepository.git");
			InitCommand command = new InitCommand();
			command.setBare(false);
			command.setGitDir(gitDir);
			command.setDirectory(gitDir);
			command.call().getRepository();
		});
