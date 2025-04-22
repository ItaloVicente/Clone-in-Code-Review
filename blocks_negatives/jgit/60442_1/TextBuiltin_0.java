		if (repository != null && repository.getDirectory() != null) {
			db = repository;
			gitdir = repository.getDirectory().getAbsolutePath();
		} else {
			db = repository;
			gitdir = gitDir;
