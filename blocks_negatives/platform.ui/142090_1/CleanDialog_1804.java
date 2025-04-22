    /**
     * Performs the actual clean operation.
     * @param cleanAll if <code>true</true> clean all projects
     * @param monitor The monitor that the build will report to
     * @throws CoreException thrown if there is a problem from the
     * core builder.
     */
    protected void doClean(boolean cleanAll, IProgressMonitor monitor)
            throws CoreException {
        if (cleanAll) {
