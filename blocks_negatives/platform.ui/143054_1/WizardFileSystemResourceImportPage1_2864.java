    /**
     * Invokes a file selection operation using the specified file system and
     * structure provider.  If the user specifies files to be imported then
     * this selection is cached for later retrieval and is returned.
     */
    protected MinimizedFileSystemElement selectFiles(
            final Object rootFileSystemObject,
            final IImportStructureProvider structureProvider) {
