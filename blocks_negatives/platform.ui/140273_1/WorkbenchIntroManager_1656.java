    private final Workbench workbench;

    /**
     * Create a new instance of the receiver.
     *
     * @param workbench the workbench instance
     */
    WorkbenchIntroManager(Workbench workbench) {
        this.workbench = workbench;
        workbench.getExtensionTracker().registerHandler(new IExtensionChangeHandler(){

            @Override
			public void addExtension(IExtensionTracker tracker,IExtension extension) {
            }
