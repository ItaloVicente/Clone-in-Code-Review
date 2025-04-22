        }
    }

    /**
     * Get the project names for the current custom build
     * order stored in the workspace description.
     *
     * @return java.lang.String[] or null if there is no setting
     */
    private String[] getCurrentBuildOrder() {
        if (notCheckedBuildOrder) {
            customBuildOrder = getWorkspace().getDescription().getBuildOrder();
            notCheckedBuildOrder = false;
        }

        return customBuildOrder;
    }

    /**
     * Get the project names in the default build order
     * based on the current Workspace settings.
     *
     * @return java.lang.String[]
     */
    private String[] getDefaultProjectOrder() {
        if (defaultBuildOrder == null) {
            IWorkspace workspace = getWorkspace();
            IWorkspace.ProjectOrder projectOrder = getWorkspace()
                    .computeProjectOrder(workspace.getRoot().getProjects());
            IProject[] foundProjects = projectOrder.projects;
            defaultBuildOrder = new String[foundProjects.length];
            int foundSize = foundProjects.length;
            for (int i = 0; i < foundSize; i++) {
                defaultBuildOrder[i] = foundProjects[i].getName();
            }
        }

        return defaultBuildOrder;
    }

    /**
     * Return the Workspace the build order is from.
     * @return org.eclipse.core.resources.IWorkspace
     */
    private IWorkspace getWorkspace() {
        return ResourcesPlugin.getWorkspace();
    }

    /**
     * Return whether or not searchElement is in testArray.
     */
    private boolean includes(String[] testArray, String searchElement) {

        for (String currentSearchElement : testArray) {
            if (searchElement.equals(currentSearchElement)) {
