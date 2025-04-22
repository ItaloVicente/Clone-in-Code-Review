		return fetchVariant(resource, 0, new NullProgressMonitor());
	}

	private IResourceVariant findFolderVariant(IResource resource,
			Repository repository) {
		File workDir = repository.getWorkTree();
		if (resource.getLocation() == null)
