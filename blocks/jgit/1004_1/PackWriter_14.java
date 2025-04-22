	private static Config configOf(final Repository repo) {
		if (repo == null)
			return new Config();
		return repo.getConfig();
	}

