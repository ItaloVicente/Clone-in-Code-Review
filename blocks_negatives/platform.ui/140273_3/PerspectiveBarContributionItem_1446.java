    /**
     * Create a new perspective contribution item
     *
     * @param perspective the descriptor for the perspective
     * @param workbenchPage the page that this perspective is in
     */
    public PerspectiveBarContributionItem(IPerspectiveDescriptor perspective,
            IWorkbenchPage workbenchPage) {
        super(perspective.getId());
        this.perspective = perspective;
        this.workbenchPage = workbenchPage;
    }
