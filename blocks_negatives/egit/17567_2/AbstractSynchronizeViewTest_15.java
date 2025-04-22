	@After
	public void deleteRepository() throws Exception {
		deleteAllProjects();
		shutDownRepositories();
		FileUtils.delete(repositoryFile.getParentFile(), FileUtils.RECURSIVE | FileUtils.RETRY);
		FileUtils.delete(childRepositoryFile.getParentFile(), FileUtils.RECURSIVE | FileUtils.RETRY);
	}

