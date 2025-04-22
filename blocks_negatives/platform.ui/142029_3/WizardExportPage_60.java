        return resourceNameField.getText();
    }

    /**
     * Return the path for the resource field.
     * @return org.eclipse.core.runtime.IPath
     */
    protected IPath getResourcePath() {
        return getPathFromText(this.resourceNameField);
    }

    /**
     * Returns this page's collection of currently-specified resources to be
     * exported. This is the primary resource selection facility accessor for
     * subclasses.
     *
     * @return the collection of resources currently selected for export (element
     *   type: <code>IResource</code>)
     */
    protected List getSelectedResources() {
        if (selectedResources == null) {
            IResource sourceResource = getSourceResource();

            if (sourceResource != null) {
