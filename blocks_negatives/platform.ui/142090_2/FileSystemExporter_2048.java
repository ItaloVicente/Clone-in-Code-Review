    /**
     *  Writes the passed resource to the specified location recursively
     */
    protected void writeResource(IResource resource, IPath destinationPath)
            throws CoreException, IOException {
        if (resource.getType() == IResource.FILE) {
