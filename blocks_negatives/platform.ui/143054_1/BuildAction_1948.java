    /**
     * Returns whether there are builders configured on the given project.
     *
     * @return <code>true</code> if it has builders,
     *   <code>false</code> if not, or if this couldn't be determined
     */
    boolean hasBuilder(IProject project) {
    	if (!project.isAccessible())
    		return false;
        try {
            ICommand[] commands = project.getDescription().getBuildSpec();
            if (commands.length > 0) {
                return true;
            }
        } catch (CoreException e) {
        }
        return false;
    }
