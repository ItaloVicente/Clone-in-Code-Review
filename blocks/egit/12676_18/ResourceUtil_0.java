	public static IContainer getContainerForLocation(IPath location) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		URI uri = URIUtil.toURI(location);
		return getContainerForLocationURI(root, uri);
	}

