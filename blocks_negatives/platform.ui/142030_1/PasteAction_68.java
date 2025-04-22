    /**
     * Returns the container to hold the pasted resources.
     */
    private IContainer getContainer() {
        List selection = getSelectedResources();
        if (selection.get(0) instanceof IFile) {
