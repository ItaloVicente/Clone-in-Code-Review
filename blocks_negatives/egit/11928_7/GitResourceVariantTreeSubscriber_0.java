
	private boolean shouldBeIncluded(IResource res) {
		if (res == null || res.isLinked(IResource.CHECK_ANCESTORS))
			return false;
		final IProject proj = res.getProject();
		if (proj == null)
			return false;
		final GitSynchronizeData d = gsds.getData(proj);
		if (d == null)
			return false;
		final Set<IContainer> includedPaths = d.getIncludedPaths();
		if (includedPaths == null)
			return true;

		IPath path = res.getLocation();
		for (IContainer container : includedPaths)
			if (container.getLocation().isPrefixOf(path))
				return true;

		return false;
	}

