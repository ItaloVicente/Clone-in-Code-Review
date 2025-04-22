        }

        String selectedDirectory = dialog.open();
        if (selectedDirectory != null) {
            previouslyBrowsedDirectory = selectedDirectory;
            locationPathField.setText(previouslyBrowsedDirectory);
            setProjectName(projectFile(previouslyBrowsedDirectory));
        }
    }

    /**
     * Returns whether this page's controls currently all contain valid
     * values.
     *
     * @return <code>true</code> if all controls are valid, and
     *   <code>false</code> if at least one is invalid
     */
    private boolean validatePage() {

        String locationFieldContents = getProjectLocationFieldValue();

            setErrorMessage(null);
            setMessage(DataTransferMessages.WizardExternalProjectImportPage_projectLocationEmpty);
            return false;
        }

        if (!path.isValidPath(locationFieldContents)) {
            setErrorMessage(DataTransferMessages.WizardExternalProjectImportPage_locationError);
            return false;
        }

        File projectFile = projectFile(locationFieldContents);
        if (projectFile == null) {
            setErrorMessage(NLS.bind(DataTransferMessages.WizardExternalProjectImportPage_notAProject, locationFieldContents));
            return false;
        }
        setProjectName(projectFile);

        if (getProjectHandle().exists()) {
            setErrorMessage(DataTransferMessages.WizardExternalProjectImportPage_projectExistsMessage);
            return false;
        }

        setErrorMessage(null);
        setMessage(null);
        return true;
    }

    private IWorkspace getWorkspace() {
        IWorkspace workspace = IDEWorkbenchPlugin.getPluginWorkspace();
        return workspace;
    }

    /**
     * Return whether or not the specifed location is a prefix
     * of the root.
     */
    private boolean isPrefixOfRoot(IPath locationPath) {
        return Platform.getLocation().isPrefixOf(locationPath);
    }

    /**
     * Set the project name using either the name of the
     * parent of the file or the name entry in the xml for
     * the file
     */
    private void setProjectName(File projectFile) {

        if (projectFile == null) {
