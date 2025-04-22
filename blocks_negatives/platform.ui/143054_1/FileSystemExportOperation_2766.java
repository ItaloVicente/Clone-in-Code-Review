    /**
     *  Create an instance of this class.  Use this constructor if you wish to
     *  recursively export a single resource
     */
    public FileSystemExportOperation(IResource res, String destinationPath,
            IOverwriteQuery overwriteImplementor) {
        super();
        resource = res;
        path = new Path(destinationPath);
        overwriteCallback = overwriteImplementor;
    }

    /**
     *  Create an instance of this class.  Use this constructor if you wish to
     *  export specific resources with a common parent resource (affects container
     *  directory creation)
     */
    public FileSystemExportOperation(IResource res, List resources,
            String destinationPath, IOverwriteQuery overwriteImplementor) {
        this(res, destinationPath, overwriteImplementor);
        resourcesToExport = resources;
    }

    /**
     *  Answer the total number of file resources that exist at or below self in the
     *  resources hierarchy.
     *
     *  @return int
     *  @param parentResource org.eclipse.core.resources.IResource
     */
    protected int countChildrenOf(IResource parentResource)
            throws CoreException {
        if (parentResource.getType() == IResource.FILE) {
