    /**
     * Create an instance of this class.
     * <p>
     * This consructor added to support calling the action from the welcome
     * page.
     * </p>
     */
    public QuickStartAction() {
        this(PlatformUI.getWorkbench().getActiveWorkbenchWindow());
    }
