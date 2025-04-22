    /**
     * Creates a project resource for the given project handle.
     *
     * @param projectHandle the handle to create a project resource
     * @param monitor the progress monitor to show visual progress
     * @return the project handle (<code>projectHandle</code>)
     * @exception CoreException if the operation fails
     * @exception OperationCanceledException if the operation is canceled
     */
    private IProject createProject(IProject projectHandle,
            IProgressMonitor monitor) throws CoreException {
