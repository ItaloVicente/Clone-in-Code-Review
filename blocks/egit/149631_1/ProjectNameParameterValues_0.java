		return Stream.of(projects)
				.parallel()
				.filter(IProject::isAccessible)
				.filter(project -> RepositoryProvider
						.getProvider(project) == null)
				.collect(
						Collectors.toMap(IProject::getName, IProject::getName));
