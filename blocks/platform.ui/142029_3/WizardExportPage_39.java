		return selectedResources;
	}

	protected IResource getSourceResource() {
		IWorkspace workspace = IDEWorkbenchPlugin.getPluginWorkspace();
		IPath testPath = getResourcePath();

		IStatus result = workspace.validatePath(testPath.toString(),
				IResource.ROOT | IResource.PROJECT | IResource.FOLDER | IResource.FILE);

		if (result.isOK() && workspace.getRoot().exists(testPath)) {
			return workspace.getRoot().findMember(testPath);
		}
