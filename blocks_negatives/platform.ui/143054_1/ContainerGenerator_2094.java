    /**
     * Ensures that this generator's container resource exists. Creates any missing
     * resource containers along the path; does nothing if the container resource
     * already exists.
     * <p>
     * Note: This method should be called within a workspace modify operation since
     * it may create resources.
     * </p>
     *
     * @param monitor a progress monitor
     * @return the container resource
     * @exception CoreException if the operation fails
     * @exception OperationCanceledException if the operation is canceled
     */
    public IContainer generateContainer(IProgressMonitor monitor)
            throws CoreException {
        IDEWorkbenchPlugin.getPluginWorkspace().run(monitor1 -> {
