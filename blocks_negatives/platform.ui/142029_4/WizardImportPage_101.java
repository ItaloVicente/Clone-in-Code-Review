        MessageDialog.open(MessageDialog.ERROR, getContainer().getShell(), IDEWorkbenchMessages.WizardImportPage_errorDialogTitle, message, SWT.SHEET);
    }

    /**
     * Returns the path of the container resource specified in the container
     * name entry field, or <code>null</code> if no name has been typed in.
     * <p>
     * The container specified by the full path might not exist and would need to
     * be created.
     * </p>
     *
     * @return the full path of the container resource specified in
     *   the container name entry field, or <code>null</code>
     */
    protected IPath getContainerFullPath() {
        IWorkspace workspace = IDEWorkbenchPlugin.getPluginWorkspace();

        IPath testPath = getResourcePath();

        IStatus result = workspace.validatePath(testPath.toString(),
                IResource.PROJECT | IResource.FOLDER);
        if (result.isOK()) {
            return testPath;
        }

        return null;
    }

    /**
     * Return the path for the resource field.
     * @return org.eclipse.core.runtime.IPath
     */
    protected IPath getResourcePath() {
        return getPathFromText(this.containerNameField);
    }

    /**
     * Returns the container resource specified in the container name entry field,
     * or <code>null</code> if such a container does not exist in the workbench.
     *
     * @return the container resource specified in the container name entry field,
     *   or <code>null</code>
     */
    protected IContainer getSpecifiedContainer() {
        IWorkspace workspace = IDEWorkbenchPlugin.getPluginWorkspace();
        IPath path = getContainerFullPath();
        if (workspace.getRoot().exists(path)) {
