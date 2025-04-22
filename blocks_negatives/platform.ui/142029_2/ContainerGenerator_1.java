    /**
     * Creates a folder resource for the given folder handle.
     *
     * @param folderHandle the handle to create a folder resource
     * @param monitor the progress monitor to show visual progress
     * @return the folder handle (<code>folderHandle</code>)
     * @exception CoreException if the operation fails
     * @exception OperationCanceledException if the operation is canceled
     */
    private IFolder createFolder(IFolder folderHandle, IProgressMonitor monitor)
            throws CoreException {
        folderHandle.create(false, true, monitor);
