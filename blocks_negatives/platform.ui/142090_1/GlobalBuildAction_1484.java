    /**
     * Creates a new action of the appropriate type. The action id is
     * <code>IWorkbenchActionConstants.BUILD</code> for incremental builds and
     * <code>IWorkbenchActionConstants.REBUILD_ALL</code> for full builds.
     *
     * @param window
     *            the window in which this action appears
     * @param type
     *            the type of build; one of
     *            <code>IncrementalProjectBuilder.INCREMENTAL_BUILD</code> or
     *            <code>IncrementalProjectBuilder.FULL_BUILD</code>
     */
    public GlobalBuildAction(IWorkbenchWindow window, int type) {
        Assert.isNotNull(window);
        this.workbenchWindow = window;
        setBuildType(type);
    }
