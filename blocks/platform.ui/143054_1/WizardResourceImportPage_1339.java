		return IDEWorkbenchMessages.WizardImportPage_errorDialogTitle;
	}

	protected IPath getContainerFullPath() {
		IWorkspace workspace = IDEWorkbenchPlugin.getPluginWorkspace();

		IPath testPath = getResourcePath();

		if (testPath.equals(workspace.getRoot().getFullPath())) {
