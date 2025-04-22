		final RepositoryMapping mapping = RepositoryMapping
				.getMapping(resource);
		if (mapping == null) {
			return;
		}
		Repository repository = mapping.getRepository();
		if (repository == null) {
			return;
		}
		String repoRelative = makeRepositoryRelative(repository, resource);
		if (repoRelative == null) {
			return;
		}

		if (ResourceUtil.isSymbolicLink(repository, repoRelativePath)) {
			extractResourceProperties(indexDiffData, resource, state);
			return;
		}

