    /**
     * Creates and returns a <code>FileSystemElement</code> if the specified
     * file system object merits one.  The criteria for this are:
     * - if the file system object is a container then it must have either a
     *   child container or an associated file
     * - if the file system object is a file then it must have an extension
     *   suitable for selection
     * 	recurse down for depth to populate children
     */
    protected FileSystemElement createElement(FileSystemElement parent,
            Object fileSystemObject, int depth) throws InterruptedException {
        ModalContext.checkCanceled(monitor);
        boolean isContainer = provider.isFolder(fileSystemObject);
        String elementLabel = parent == null ? provider
                .getFullPath(fileSystemObject) : provider
                .getLabel(fileSystemObject);
