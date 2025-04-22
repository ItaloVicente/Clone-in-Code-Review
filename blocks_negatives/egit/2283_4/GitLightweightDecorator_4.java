		if (Team.isIgnoredHint(resource))
			return;

		final RepositoryMapping mapping = RepositoryMapping
				.getMapping(resource);
		if (mapping == null)
			return;

		if (mapping.getRepoRelativePath(resource) == null)
			return;

