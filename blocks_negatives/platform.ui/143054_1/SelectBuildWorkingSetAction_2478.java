    public SelectBuildWorkingSetAction(IWorkbenchWindow window,
            IActionBarConfigurer actionBars) {
        super(IDEWorkbenchMessages.SelectWorkingSetAction_text);
        this.window = window;
        this.actionBars = actionBars;
    }
