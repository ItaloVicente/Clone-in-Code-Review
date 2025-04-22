    /**
     * Create a new instance of the receiver.
     * @param window
     * @param actionBars
     */
    public BuildSetMenu(IWorkbenchWindow window, IActionBarConfigurer actionBars) {
        this.window = window;
        this.actionBars = actionBars;
        selectBuildWorkingSetAction = new SelectBuildWorkingSetAction(window,
                actionBars);
    }
