    /**
     * Returns the workbench. Fails if the workbench has not been created yet.
     *
     * @return the workbench
     */
    public static IWorkbench getWorkbench() {
        if (Workbench.getInstance() == null) {
            throw new IllegalStateException(WorkbenchMessages.PlatformUI_NoWorkbench);
        }
        return Workbench.getInstance();
    }
