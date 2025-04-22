	@After
	public void deleteRepositories() throws Exception {
		deleteAllProjects();
		shutDownRepositories();
		for (Repository r : reposToDelete) {
			if (r.isBare())
				FileUtils.delete(r.getDirectory(), FileUtils.RECURSIVE
						| FileUtils.RETRY);
			else
				FileUtils.delete(r.getWorkTree(), FileUtils.RECURSIVE
						| FileUtils.RETRY);
		}
	}

