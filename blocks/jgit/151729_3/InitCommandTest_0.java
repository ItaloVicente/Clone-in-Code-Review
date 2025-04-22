	public void testInitBare_DirAndGitDirMustBeEqual()
			throws JGitInternalException {
		assertThrows(IllegalStateException.class
			File gitDir = createTempDirectory("testInitRepository.git");
			InitCommand command = new InitCommand();
			command.setBare(true);
			command.setDirectory(gitDir);
			command.setGitDir(new File(gitDir
			command.call();
		});
