		final Iterable<? extends IResource> includedResourceIterable;
		if (includedResources == null)
			includedResourceIterable = Arrays.asList(ROOT.getProjects());
		else
			includedResourceIterable = includedResources;
		for (IResource res : includedResourceIterable) {
			IProject project = res.getProject();
