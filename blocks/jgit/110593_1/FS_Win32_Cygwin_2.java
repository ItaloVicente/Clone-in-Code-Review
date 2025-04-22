		final Path path;
		try {
			path = FileUtils.toPath(gitdir);
		} catch (IOException ex) {
			return null;
		}

		final Path hookPath = path.resolve(Constants.HOOKS)
