	private File getDirectoryContainingRepo(Repository repo) {
		if (!repo.isBare())
			return repo.getDirectory().getParentFile();
		else
			return repo.getDirectory();
	}

