
	@Nullable
	public static IResource adaptToAnyResource(Object object) {
		if (object == null) {
			return null;
		}
		IResource resource = adapt(object, IResource.class);
		if (resource != null) {
			return resource;
		}
		resource = adapt(object, IFile.class);
		if (resource != null) {
			return resource;
		}
		resource = adapt(object, IProject.class);
		if (resource != null) {
			return resource;
		}
		resource = adapt(object, IContainer.class);
		if (resource != null) {
			return resource;
		}
		return null;
	}
