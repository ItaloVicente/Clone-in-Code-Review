	@After
	public void tearDown() throws Exception {
		deleteAllProjects();
		shutDownRepositories();
		FileUtils.delete(gitDir.getParentFile(), FileUtils.RECURSIVE
				| FileUtils.RETRY);
		FileUtils.delete(standaloneDirectory, FileUtils.RECURSIVE
				| FileUtils.RETRY);
	}

