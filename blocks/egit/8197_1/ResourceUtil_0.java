	public static IContainer getContainerForLocation(Repository repository,
			String repoRelativePath) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IPath path = new Path(repository.getWorkTree().getAbsolutePath()).append(repoRelativePath);
		return root.getContainerForLocation(path);
	}

