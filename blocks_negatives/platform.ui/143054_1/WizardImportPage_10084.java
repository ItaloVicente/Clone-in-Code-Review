    /**
     * Opens a container selection dialog and displays the user's subsequent
     * container resource selection in this page's container name field.
     */
    protected void handleContainerBrowseButtonPressed() {
        IPath containerPath = queryForContainer(getSpecifiedContainer(),
                IDEWorkbenchMessages.WizardImportPage_selectFolderLabel);
