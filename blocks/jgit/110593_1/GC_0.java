		final Path path;
		try {
			path = FileUtils.toPath(repo.getObjectsDirectory());
		} catch (IOException e) {
			return false;
		}

