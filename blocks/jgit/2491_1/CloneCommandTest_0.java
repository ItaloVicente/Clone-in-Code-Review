	@Test
	public void shouldCloneToABareRepository() throws Exception {
		File directory = createTempDirectory("testCloneRepositoryWithBranch.git");
		CloneCommand command = Git.cloneRepository();
		command.setBranch("refs/heads/test");
		command.setDirectory(directory);
		command.setBare(true);
		assertNotNull(command.call());
	}

