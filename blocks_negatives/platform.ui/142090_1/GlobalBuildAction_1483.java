    /**
     * Creates a new action of the appropriate type. The action id is
     * <code>IWorkbenchActionConstants.BUILD</code> for incremental builds and
     * <code>IWorkbenchActionConstants.REBUILD_ALL</code> for full builds.
     *
     * @param workbench
     *            the active workbench
     * @param shell
     *            the shell for any dialogs
     * @param type
     *            the type of build; one of
     *            <code>IncrementalProjectBuilder.INCREMENTAL_BUILD</code> or
     *            <code>IncrementalProjectBuilder.FULL_BUILD</code>
     *
     * @deprecated use GlobalBuildAction(IWorkbenchWindow, type) instead
     */
    @Deprecated
