	@After
	public void tearDown() throws Exception {
		deleteAllProjects();
		shutDownRepositories();
		FileUtils.delete(repositoryFile.getParentFile(), FileUtils.RECURSIVE
				| FileUtils.RETRY);
	}

