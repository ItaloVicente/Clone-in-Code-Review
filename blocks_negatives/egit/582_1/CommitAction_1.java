		if (repository != null) {
			final RepositoryConfig config = repository.getConfig();
			author = config.getAuthorName();
			final String authorEmail = config.getAuthorEmail();

			committer = config.getCommitterName();
			final String committerEmail = config.getCommitterEmail();
		}
