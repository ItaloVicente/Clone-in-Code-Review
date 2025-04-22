    /**
     *  Creates the specified file system directory at <code>destinationPath</code>.
     *  This creates a new file system directory.
     *
     *  @param destinationPath location to which files will be written
     */
    public void createFolder(IPath destinationPath) {
        new File(destinationPath.toOSString()).mkdir();
    }
