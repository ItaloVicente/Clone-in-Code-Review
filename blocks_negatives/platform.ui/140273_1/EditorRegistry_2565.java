     * Find the file editor mapping for the file extension. Returns
     * <code>null</code> if not found.
     *
     * @param ext
     *            the file extension
     * @return the mapping, or <code>null</code>
     */
    private FileEditorMapping getMappingFor(String ext) {
        if (ext == null) {
