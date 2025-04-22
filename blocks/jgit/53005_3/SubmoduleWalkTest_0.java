	@Test
	public void bareRepositoryWithNoSubmodules() throws IOException {
		FileRepository bareRepo = createBareRepository();
		boolean result = SubmoduleWalk.containsGitModulesFile(bareRepo);
		assertFalse(result);
	}

