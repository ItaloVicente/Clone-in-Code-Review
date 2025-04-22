        updateWidgetEnablements();
        setPageComplete(determinePageCompletion());

        setControl(composite);
    }

    /**
     * Creates the export destination specification visual components.
     * <p>
     * Subclasses must implement this method.
     * </p>
     *
     * @param parent the parent control
     */
    protected abstract void createDestinationGroup(Composite parent);

    /**
     * Creates the checkbox tree and list for selecting resources.
     *
     * @param parent the parent control
     */
    protected final void createResourcesGroup(Composite parent) {

        List input = new ArrayList();
        IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
                .getProjects();
        for (IProject project : projects) {
            if (project.isOpen()) {
