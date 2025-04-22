    /**
     * Creates a <code>ZipFileStructureProvider</code>, which will operate
     * on the passed zip file.
     *
     * @param sourceFile the zip file used to create this structure provider
     */
    public ZipFileStructureProvider(ZipFile sourceFile) {
        super();
        zipFile = sourceFile;
    }

    /**
     * Adds the specified child to the internal collection of the parent's children.
     */
    protected void addToChildren(ZipEntry parent, ZipEntry child) {
