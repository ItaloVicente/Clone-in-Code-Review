    /**
     * Returns whether there are builders configured on the given project.
     *
     * @return <code>true</code> if it has builders,
     *   <code>false</code> if not, or if this could not be determined
     */
    boolean hasBuilder(IProject project) {
        try {
            ICommand[] commands = project.getDescription().getBuildSpec();
            if (commands.length > 0) {
