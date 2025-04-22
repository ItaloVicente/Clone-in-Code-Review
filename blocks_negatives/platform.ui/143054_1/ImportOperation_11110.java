     * Creates a new operation that recursively imports the entire contents of the
     * specified root file system object.
     * <p>
     * The <code>source</code> parameter represents the root file system object to
     * import. All contents of this object are imported. Valid types for this parameter
     * are determined by the supplied <code>IImportStructureProvider</code>.
     * </p>
     * <p>
     * The <code>provider</code> parameter allows this operation to deal with the
     * source object in an abstract way. This operation calls methods on the provider
     * and the provider in turn calls specific methods on the source object.
     * </p>
     *  <p>
     * The default import behavior is to recreate the complete container structure
     * for the contents of the root file system object in their destination.
     * If <code>setCreateContainerStructure</code> is set to false then the container
     * structure created is relative to the root file system object.
     * </p>
     *
     * @param containerPath the full path of the destination container within the
     *   workspace
     * @param source the root file system object to import
     * @param provider the file system structure provider to use
     * @param overwriteImplementor the overwrite strategy to use
     */
    public ImportOperation(IPath containerPath, Object source,
            IImportStructureProvider provider,
            IOverwriteQuery overwriteImplementor) {
        super();
        this.destinationPath = containerPath;
        this.source = source;
        this.provider = provider;
        overwriteCallback = overwriteImplementor;
    }

    /**
     * Creates a new operation that imports specific file system objects.
     * In this usage context, the specified source file system object is used by the
     * operation solely to determine the destination container structure of the file system
     * objects being imported.
     * <p>
     * The <code>source</code> parameter represents the root file system object to
     * import. Valid types for this parameter are determined by the supplied
     * <code>IImportStructureProvider</code>. The contents of the source which
     * are to be imported are specified in the <code>filesToImport</code>
     * parameter.
     * </p>
     * <p>
     * The <code>provider</code> parameter allows this operation to deal with the
     * source object in an abstract way. This operation calls methods on the provider
     * and the provider in turn calls specific methods on the source object.
     * </p>
     * <p>
     * The <code>filesToImport</code> parameter specifies what contents of the root
     * file system object are to be imported.
     * </p>
     * <p>
     * The default import behavior is to recreate the complete container structure
     * for the file system objects in their destination. If <code>setCreateContainerStructure</code>
     * is set to <code>false</code>, then the container structure created for each of
     * the file system objects is relative to the supplied root file system object.
     * </p>
     *
     * @param containerPath the full path of the destination container within the
     *   workspace
     * @param source the root file system object to import from
     * @param provider the file system structure provider to use
     * @param overwriteImplementor the overwrite strategy to use
     * @param filesToImport the list of file system objects to be imported
     *  (element type: <code>Object</code>)
     */
    public ImportOperation(IPath containerPath, Object source,
            IImportStructureProvider provider,
            IOverwriteQuery overwriteImplementor, List filesToImport) {
        this(containerPath, source, provider, overwriteImplementor);
        setFilesToImport(filesToImport);
    }

    /**
     * Creates a new operation that imports specific file system objects.
     * <p>
     * The <code>provider</code> parameter allows this operation to deal with the
     * source object in an abstract way. This operation calls methods on the provider
     * and the provider in turn calls specific methods on the source object.
     * </p>
     * <p>
     * The <code>filesToImport</code> parameter specifies what file system objects
     * are to be imported.
     * </p>
     * <p>
     * The default import behavior is to recreate the complete container structure
     * for the file system objects in their destination. If <code>setCreateContainerStructure</code>
     * is set to <code>false</code>, then no container structure is created for each of
     * the file system objects.
     * </p>
     *
     * @param containerPath the full path of the destination container within the
     *   workspace
     * @param provider the file system structure provider to use
     * @param overwriteImplementor the overwrite strategy to use
     * @param filesToImport the list of file system objects to be imported
     *  (element type: <code>Object</code>)
     */
    public ImportOperation(IPath containerPath,
            IImportStructureProvider provider,
            IOverwriteQuery overwriteImplementor, List filesToImport) {
        this(containerPath, null, provider, overwriteImplementor);
        setFilesToImport(filesToImport);
    }

    /**
     * Prompts if existing resources should be overwritten. Recursively collects
     * existing read-only files to overwrite and resources that should not be
     * overwritten.
     *
     * @param sourceStart destination path to check for existing files
     * @param sources file system objects that may exist in the destination
     * @param noOverwrite files that were selected to be skipped (don't overwrite).
     * 	object type IPath
     * @param overwriteReadonly the collected existing read-only files to overwrite.
     * 	object type IPath
     * @param policy on of the POLICY constants defined in the
     * class.
     */
