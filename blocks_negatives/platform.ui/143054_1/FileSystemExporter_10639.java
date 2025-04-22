    /**
     *  Exports the passed container's children
     */
    protected void writeChildren(IContainer folder, IPath destinationPath)
            throws CoreException, IOException {
        if (folder.isAccessible()) {
