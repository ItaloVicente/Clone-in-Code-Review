		Object input = getInput(event);
		if (!(input instanceof IFile))
			return null;
		IFile resource = (IFile) input;
		final RepositoryMapping map = RepositoryMapping.getMapping(resource);
		final String gitPath = map.getRepoRelativePath(resource);
		Iterator<?> it = selection.iterator();
