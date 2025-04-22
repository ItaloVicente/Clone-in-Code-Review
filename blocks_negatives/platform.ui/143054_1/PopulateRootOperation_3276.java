    /**
     * Create a new <code>PopulateFilesOperation</code>.
     * @param rootObject the object to be populated
     * @param structureProvider the object that defines how we are to populate it.
     */
    public PopulateRootOperation(Object rootObject,
            IImportStructureProvider structureProvider) {
        super(rootObject, structureProvider);
    }
