    /**
     * Creates a project resource handle for the project with the given name.
     * This method does not create the project resource; this is the responsibility
     * of <code>createProject</code>.
     *
     * @param root the workspace root resource
     * @param projectName the name of the project
     * @return the new project resource handle
     */
    private IProject createProjectHandle(IWorkspaceRoot root, String projectName) {
        return root.getProject(projectName);
    }
