        }
        return true;
    }

    /**
     * Sets the context for use by the VCM provider to prompt the user
     * for check-out of files.
     *
     * @param shell context for use by the VCM provider to prompt user
     * 	for check-out. The user will not be prompted if set to <code>null</code>.
     * @see IWorkspace#validateEdit(org.eclipse.core.resources.IFile[], java.lang.Object)
     * @since 2.1
     */
    public void setContext(Shell shell) {
        context = shell;
    }

    /**
     * Sets whether the containment structures that are implied from the full paths
     * of file system objects being imported should be duplicated in the workbench.
     *
     * @param value <code>true</code> if containers should be created, and
     *  <code>false</code> otherwise
     */
    public void setCreateContainerStructure(boolean value) {
        createContainerStructure = value;
    }

    /**
     * Sets the file system objects to import.
     *
     * @param filesToImport the list of file system objects to be imported
     *   (element type: <code>Object</code>)
     */
    public void setFilesToImport(List filesToImport) {
        this.selectedFiles = filesToImport;
    }

    /**
     * Sets whether imported file system objects should automatically overwrite
     * existing workbench resources when a conflict occurs.
     *
     * @param value <code>true</code> to automatically overwrite, and
     *   <code>false</code> otherwise
     */
    public void setOverwriteResources(boolean value) {
        if (value) {
