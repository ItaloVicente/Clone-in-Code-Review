	@NonNull
	public static IResource getResourceHandleForLocation(@NonNull IPath path) {
		final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace()
				.getRoot();

		final IResource resource;
		if (path.segmentCount() > 1)
			resource = workspaceRoot.getFile(path);
		else
			resource = workspaceRoot.getProject(path.toString());
		return resource;
