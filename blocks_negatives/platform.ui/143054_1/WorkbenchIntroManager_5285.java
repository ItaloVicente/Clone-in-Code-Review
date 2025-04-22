    /**
     * Create a new instance of the receiver.
     *
     * @param workbench the workbench instance
     */
    WorkbenchIntroManager(Workbench workbench) {
        this.workbench = workbench;
        workbench.getExtensionTracker().registerHandler(new IExtensionChangeHandler(){
