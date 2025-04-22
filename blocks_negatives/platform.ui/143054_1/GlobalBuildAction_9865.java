    /**
     * Returns an array of all projects in the workspace
     */
    /* package */IProject[] getWorkspaceProjects() {
        return ResourcesPlugin.getWorkspace().getRoot().getProjects();
    }
