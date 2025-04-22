    /**
     * Creates a new ToggleAutoBuildAction
     * @param window The window for parenting dialogs associated with this action
     */
    public ToggleAutoBuildAction(IWorkbenchWindow window) {
        super(IDEWorkbenchMessages.Workbench_buildAutomatically);
        this.window = window;
        setChecked(ResourcesPlugin.getWorkspace().isAutoBuilding());
    }
