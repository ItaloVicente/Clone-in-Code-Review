	private boolean shouldBeIncluded(IResource res,
			GitSynchronizeDataSet dataSet) {
		final IProject project = res.getProject();
		if (project == null)
			return false;

		final GitSynchronizeData syncData = dataSet.getData(project);
		if (syncData == null)
			return false;

		final Set<? extends IResource> includedPaths = syncData
				.getIncludedPaths();
		if (includedPaths == null)
			return true;

		IPath path = res.getLocation();
		for (IResource resource : includedPaths)
			if (resource.getLocation().isPrefixOf(path))
				return true;

		return false;
	}
