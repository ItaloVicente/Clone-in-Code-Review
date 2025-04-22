	public static RepositoryMapping getMappingAllowUnmappedProject(final IResource resource) {
		final IProject project = resource.getProject();
		if (project == null)
			return null;

		final RepositoryProvider rp = RepositoryProvider.getProvider(project);
		if (!(rp instanceof GitProvider))
			return null;

		GitProjectData data = ((GitProvider)rp).getData();
		if (data == null)
			return null;

		RepositoryMapping mapping = data.getRepositoryMapping(resource);
		if (mapping != null)
			return mapping;

		if (resource.getType() != IResource.PROJECT)
			return null;

		return data.getSingleRepositoryMapping();
	}

