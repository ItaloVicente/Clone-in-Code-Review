		db = repository;
		gitdir = gitDir;
		if (repository != null) {
			File directory = repository.getDirectory();
			if (directory != null) {
				gitdir = directory.getAbsolutePath();
			}
