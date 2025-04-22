		if (repository != null) {
			final UserConfig config = repository.getConfig().get(UserConfig.KEY);
			author = config.getAuthorName();
			final String authorEmail = config.getAuthorEmail();

			committer = config.getCommitterName();
			final String committerEmail = config.getCommitterEmail();
		}
