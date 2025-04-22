	/**
	 * Returns an handle to the workspace resource at the given location.
	 * <p>
	 * This is a resource handle operation; neither the resource nor the result
	 * need exist in the workspace.
	 * </p>
	 *
	 * @param pathString
	 *            Git path for which we seek a workspace resource.
	 * @return The resource pointed by {@code pathString}.
	 */
	private static IResource getResourceForGitPath(String pathString) {
		final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace()
				.getRoot();

		final IPath path = new Path(pathString);
		final IResource resource;
		if (path.segmentCount() > 1)
			resource = workspaceRoot.getFile(path);
		else
			resource = workspaceRoot.getProject(pathString);
		return resource;
	}

