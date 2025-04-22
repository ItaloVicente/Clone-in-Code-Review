    /**
     * Invokes a build on all projects within the workspace. Reports any errors
     * with the build to the user.
     */
    /* package */void doBuildOperation() {
        Job buildJob = new Job(IDEWorkbenchMessages.GlobalBuildAction_jobTitle) {
            @Override
