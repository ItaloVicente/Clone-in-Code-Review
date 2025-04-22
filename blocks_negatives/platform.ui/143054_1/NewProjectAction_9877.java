    /**
     * This default constructor allows the the action to be called from the welcome page.
     */
    public NewProjectAction() {
        this(PlatformUI.getWorkbench().getActiveWorkbenchWindow());
    }
