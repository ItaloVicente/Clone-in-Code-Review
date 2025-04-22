        return IDEWorkbenchMessages.WizardImportPage_errorDialogTitle;
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

        if (testPath.equals(workspace.getRoot().getFullPath())) {
