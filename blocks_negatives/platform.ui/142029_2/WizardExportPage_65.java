        setPageComplete(determinePageCompletion());
        updateWidgetEnablements();
    }

    /**
     * Opens a container selection dialog and displays the user's subsequent
     * container selection in this page's resource name field.
     */
    protected void handleResourceBrowseButtonPressed() {
        IResource currentFolder = getSourceResource();
        if (currentFolder != null && currentFolder.getType() == IResource.FILE) {
