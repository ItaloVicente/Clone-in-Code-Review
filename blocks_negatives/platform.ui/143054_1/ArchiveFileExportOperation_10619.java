        this(res, filename);
        resourcesToExport = resources;
    }

    /**
     * Add a new entry to the error table with the passed information
     */
    protected void addError(String message, Throwable e) {
        errorTable.add(new Status(IStatus.ERROR,
                IDEWorkbenchPlugin.IDE_WORKBENCH, 0, message, e));
    }

    /**
     *  Answer the total number of file resources that exist at or below self
     *  in the resources hierarchy.
     *
     *  @return int
     *  @param checkResource org.eclipse.core.resources.IResource
     */
    protected int countChildrenOf(IResource checkResource) throws CoreException {
        if (checkResource.getType() == IResource.FILE) {
