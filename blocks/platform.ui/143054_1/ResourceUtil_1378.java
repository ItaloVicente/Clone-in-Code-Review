	private static IFile getFileFromResourceMapping(ResourceMapping mapping) {
		IResource resource = getResourceFromResourceMapping(mapping);
		if (resource instanceof IFile) {
			return (IFile) resource;
		}
		return null;
	}

	private static IResource getResourceFromResourceMapping(ResourceMapping mapping) {
