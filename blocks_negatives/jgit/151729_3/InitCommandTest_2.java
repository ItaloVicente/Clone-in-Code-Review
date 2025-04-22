			throws JGitInternalException, GitAPIException, IOException {
		File gitDir = createTempDirectory("testInitRepository.git");
		InitCommand command = new InitCommand();
		command.setBare(false);
		command.setGitDir(gitDir);
		command.setDirectory(gitDir);
		command.call().getRepository();
