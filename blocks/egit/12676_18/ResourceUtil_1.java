	private static IContainer getContainerForLocationURI(IWorkspaceRoot root,
			URI uri) {
		IContainer[] containers = root.findContainersForLocationURI(uri);
		return getExistingResourceWithShortestPath(containers);
	}

