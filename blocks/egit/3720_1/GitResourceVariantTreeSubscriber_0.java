	private boolean shouldBeIncluded(IResource res) {
		Set<IContainer> includedPaths = gsds.getData(res.getProject())
				.getIncludedPaths();
		if (includedPaths == null)
			return true;

		IPath path = res.getLocation();
		for (IContainer container : includedPaths)
			if (container.getLocation().isPrefixOf(path))
				return true;

		return false;
	}

