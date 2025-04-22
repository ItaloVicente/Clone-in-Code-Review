		return IDEWorkbenchMessages.WizardImportPage_errorDialogTitle;
	}

	protected IPath getContainerFullPath() {
		IWorkspace workspace = IDEWorkbenchPlugin.getPluginWorkspace();

		IPath testPath = getResourcePath();

		if (testPath.equals(workspace.getRoot().getFullPath())) {
			return testPath;
		}

		IStatus result = workspace.validatePath(testPath.toString(),
				IResource.PROJECT | IResource.FOLDER | IResource.ROOT);
		if (result.isOK()) {
