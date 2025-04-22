        return new Path(((ZipEntry) element).getName()).lastSegment();
    }

    /**
     * Returns the entry that this importer uses as the root sentinel.
     *
     * @return java.util.zip.ZipEntry
     */
    public ZipEntry getRoot() {
        return root;
    }

    /**
     * Returns the zip file that this provider provides structure for.
     *
     * @return the zip file this provider provides structure for
     */
    public ZipFile getZipFile() {
        return zipFile;
    }

    /**
     * Initializes this object's children table based on the contents of
     * the specified source file.
     */
    protected void initialize() {
