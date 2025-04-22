        return null;
    }

    /**
     * Creates a new project resource with the selected name.
     * <p>
     * In normal usage, this method is invoked after the user has pressed Finish on
     * the wizard; the enablement of the Finish button implies that all controls
     * on the pages currently contain valid values.
     * </p>
     *
     * @return the created project resource, or <code>null</code> if the project
     *    was not created
     */
    IProject createExistingProject() {

        String projectName = projectNameField.getText();
        final IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final IProject project = workspace.getRoot().getProject(projectName);
        if (this.description == null) {
            this.description = workspace.newProjectDescription(projectName);
            IPath locationPath = getLocationPath();
            if (isPrefixOfRoot(locationPath)) {
