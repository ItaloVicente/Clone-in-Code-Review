		if (!(selected instanceof IProject))
			return;
		RepositoryMapping mapping = RepositoryMapping
				.getMapping((IProject) selected);
		if (mapping == null)
			return;
		final Repository repository = mapping.getRepository();
		if (repository == null)
			return;
