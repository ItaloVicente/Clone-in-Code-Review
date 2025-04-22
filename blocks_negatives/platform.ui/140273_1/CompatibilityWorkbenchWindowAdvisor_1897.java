    private WorkbenchAdvisor wbAdvisor;

    /**
     * Creates a new compatibility workbench window advisor.
     *
     * @param wbAdvisor the workbench advisor
     * @param windowConfigurer the window configurer
     */
    public CompatibilityWorkbenchWindowAdvisor(WorkbenchAdvisor wbAdvisor, IWorkbenchWindowConfigurer windowConfigurer) {
        super(windowConfigurer);
        this.wbAdvisor = wbAdvisor;
    }

    @Override
