    /**
     * Creates a new workbench window advisor for configuring a workbench
     * window via the given workbench window configurer.
     *
     * @param configurer an object for configuring the workbench window
     */
    public WorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        Assert.isNotNull(configurer);
        this.windowConfigurer = configurer;
    }
