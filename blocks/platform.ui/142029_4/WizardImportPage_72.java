		MessageDialog.open(MessageDialog.ERROR, getContainer().getShell(),
				IDEWorkbenchMessages.WizardImportPage_errorDialogTitle, message, SWT.SHEET);
	}

	protected IPath getContainerFullPath() {
		IWorkspace workspace = IDEWorkbenchPlugin.getPluginWorkspace();

		IPath testPath = getResourcePath();

		IStatus result = workspace.validatePath(testPath.toString(), IResource.PROJECT | IResource.FOLDER);
		if (result.isOK()) {
			return testPath;
		}

		return null;
	}

	protected IPath getResourcePath() {
		return getPathFromText(this.containerNameField);
	}

	protected IContainer getSpecifiedContainer() {
		IWorkspace workspace = IDEWorkbenchPlugin.getPluginWorkspace();
		IPath path = getContainerFullPath();
		if (workspace.getRoot().exists(path)) {
