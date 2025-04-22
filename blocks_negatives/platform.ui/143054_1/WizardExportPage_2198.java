    }

    /**
     * Set the resource whos name we will display.
     * @param resource
     */
    protected void setResourceToDisplay(IResource resource) {
        setResourceFieldValue(resource.getFullPath().makeRelative().toString());
    }

    /**
     * Sets the value of this page's "Types to export" field, or stores
     * it for future use if this visual component does not exist yet.
     *
     * @param value new value
     */
    public void setTypesFieldValue(String value) {
        if (typesToExportField == null) {
