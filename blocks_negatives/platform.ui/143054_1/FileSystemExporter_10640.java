    /**
     *  Writes the passed file resource to the specified destination on the local
     *  file system
     */
    protected void writeFile(IFile file, IPath destinationPath)
            throws IOException, CoreException {
        OutputStream output = null;
        InputStream contentStream = null;
