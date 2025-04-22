    /**
     * Creates a new action of the appropriate type. The action id is
     * <code>ID_BUILD</code> for incremental builds and <code>ID_REBUILD_ALL</code>
     * for full builds.
     *
     * @param shell the shell for any dialogs
     * @param type the type of build; one of
     *  <code>IncrementalProjectBuilder.INCREMENTAL_BUILD</code> or
     *  <code>IncrementalProjectBuilder.FULL_BUILD</code>
     * @deprecated See {@link #BuildAction(IShellProvider, int)}
     */
    @Deprecated
