		String author;
		if (repository.getRepositoryState() == RepositoryState.CHERRY_PICKING_RESOLVED)
			author = getCherryPickOriginalAuthor(repository);
		else {
			author = config.getAuthorName();
			final String authorEmail = config.getAuthorEmail();
		}
