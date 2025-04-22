    /**
     * Creates a new <code>FileSystemElement</code> and initializes it and its
     * parent if applicable.
     *
     * @param name
     *            The name of the element
     * @param parent
     *            The parent element. May be <code>null</code>
     * @param isDirectory
     *            if <code>true</code> this is representing a directory,
     *            otherwise it is a file.
     */
    public FileSystemElement(String name, FileSystemElement parent,
            boolean isDirectory) {
        this.name = name;
        this.parent = parent;
        this.isDirectory = isDirectory;
        if (parent != null) {
