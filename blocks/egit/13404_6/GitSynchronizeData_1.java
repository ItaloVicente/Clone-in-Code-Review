		final Iterator<IResource> includedResourceIterator;
		if (includedResources == null) {
			includedResourceIterator = new ArrayIterator<IResource>(ROOT.getProjects());
		} else {
			includedResourceIterator = includedResources.iterator();
		}
		while (includedResourceIterator.hasNext()) {
			IResource res = includedResourceIterator.next();
			IProject project = res.getProject();
