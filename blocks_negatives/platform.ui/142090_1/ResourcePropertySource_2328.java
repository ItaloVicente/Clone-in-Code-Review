    /**
     * Get the java.io.File equivalent of the passed
     * IFile. If the location does not exist then return
     * <code>null</code>
     * @param resource the resource to lookup
     * @return java.io.File or <code>null</code>.
     */
    protected File getFile(IResource resource) {
        IPath location = resource.getLocation();
        if (location == null) {
