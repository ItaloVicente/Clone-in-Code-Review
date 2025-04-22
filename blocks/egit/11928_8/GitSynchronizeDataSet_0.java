	public boolean shouldBeIncluded(IResource res) {
		final IProject project = res.getProject();
		if (project == null)
			return false;

		final GitSynchronizeData syncData = getData(project);
		if (syncData == null)
			return false;

		final Set<? extends IResource> includedPaths = syncData
				.getIncludedPaths();
		if (includedPaths == null)
			return true;

		IPath path = res.getLocation();
		if (path != null) {
			for (IResource resource : includedPaths) {
				IPath inclResourceLocation = resource.getLocation();
				if (inclResourceLocation != null
						&& inclResourceLocation.isPrefixOf(path))
					return true;
			}
		}
		return false;
	}
