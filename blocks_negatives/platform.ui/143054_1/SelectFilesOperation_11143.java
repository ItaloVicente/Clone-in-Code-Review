    /**
     * Returns a boolean indicating whether the extension of the passed filename
     * is one of the extensions specified as desired by the filter.
     */
    protected boolean hasDesiredExtension(String filename) {
        if (desiredExtensions == null) {
