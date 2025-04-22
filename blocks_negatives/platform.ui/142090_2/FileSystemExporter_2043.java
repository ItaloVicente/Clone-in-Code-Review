    /**
     *  Writes the passed resource to the specified location recursively.
     *
     *  @param resource the resource to write out to the file system
     *  @param destinationPath location where the resource will be written
     *  @exception CoreException if the operation fails
     *  @exception IOException if an I/O error occurs when writing files
     */
    public void write(IResource resource, IPath destinationPath)
            throws CoreException, IOException {
        if (resource.getType() == IResource.FILE) {
