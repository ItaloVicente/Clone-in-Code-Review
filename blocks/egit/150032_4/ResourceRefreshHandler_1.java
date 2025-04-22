
	private static IContainer getContainerForLocation(@NonNull IPath location) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IContainer dir = root.getContainerForLocation(location);
		if (dir == null) {
			return null;
		}
		if (isValid(dir)) {
			return dir;
		}
		URI uri = URIUtil.toURI(location);
		IContainer[] containers = root.findContainersForLocationURI(uri);
		return Arrays.stream(containers).filter(ResourceRefreshHandler::isValid)
				.findFirst().orElse(null);
	}

	private static boolean isValid(@NonNull IResource resource) {
		return resource.isAccessible()
				&& !resource.isLinked(IResource.CHECK_ANCESTORS);
	}

