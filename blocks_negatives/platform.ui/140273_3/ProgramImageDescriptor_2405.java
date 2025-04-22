    /**
     * Creates a new ImageDescriptor. The image is loaded
     * from a file with the given name <code>name</code>.
     */
    public ProgramImageDescriptor(String fullPath, int offsetInFile) {
        filename = fullPath;
        offset = offsetInFile;
    }
