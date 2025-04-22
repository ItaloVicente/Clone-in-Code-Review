	static IDecoratableResource createTemporaryDecoratableResource(
			final IProject project) throws IOException {
		final DecoratableResource decoratableResource = new DecoratableResource(
				project);
		final Repository repository = RepositoryMapping.getMapping(project)
				.getRepository();
		decoratableResource.repositoryName = getRepositoryName(repository);
		decoratableResource.branch = getShortBranch(repository);
		return decoratableResource;
	}

