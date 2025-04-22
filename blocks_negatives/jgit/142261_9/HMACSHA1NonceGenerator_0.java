		String path;
		if (repo instanceof DfsRepository) {
			path = ((DfsRepository) repo).getDescription().getRepositoryName();
		} else {
			File directory = repo.getDirectory();
			if (directory != null) {
				path = directory.getPath();
			} else {
				throw new IllegalStateException();
			}
		}

