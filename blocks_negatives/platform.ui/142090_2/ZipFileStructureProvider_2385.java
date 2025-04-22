        ZipEntry newEntry = new ZipEntry(pathname.toString());
        directoryEntryCache.put(pathname, newEntry);
        addToChildren(parent, newEntry);
    }

    /**
     * Creates a new file zip entry with the specified name.
     */
    protected void createFile(ZipEntry entry) {
        IPath pathname = new Path(entry.getName());
        ZipEntry parent;
        if (pathname.segmentCount() == 1) {
