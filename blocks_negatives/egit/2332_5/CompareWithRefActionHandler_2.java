		final IResource[] selectedResources = getSelectedResources();
		if (selectedResources.length != 1)
			return false;

		final IResource resource = selectedResources[0];
		final RepositoryMapping mapping = RepositoryMapping.getMapping(resource
				.getProject());
		return mapping != null;
