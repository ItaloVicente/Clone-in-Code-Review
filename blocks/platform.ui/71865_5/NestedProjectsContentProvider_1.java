		final Set<IContainer> parentsToRefresh = new LinkedHashSet<>();
		IResource resource = event.getResource();
		if (event.getType() == IResourceChangeEvent.PRE_DELETE && resource != null
				&& resource.getType() == IResource.PROJECT) {
			IProject aboutToBeDeleted = (IProject) resource;
			IContainer parent = getParent(aboutToBeDeleted);
			if (parent != null) {
				parentsToRefresh.add(parent);
