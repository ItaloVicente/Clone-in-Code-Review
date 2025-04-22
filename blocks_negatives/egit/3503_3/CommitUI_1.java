		String author = null;
		String committer = null;
		final UserConfig config = repo.getConfig().get(UserConfig.KEY);
		author = config.getAuthorName();
		final String authorEmail = config.getAuthorEmail();

		committer = config.getCommitterName();
		final String committerEmail = config.getCommitterEmail();

