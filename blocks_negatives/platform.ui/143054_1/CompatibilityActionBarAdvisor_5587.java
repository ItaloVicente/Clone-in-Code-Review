    /**
     * Creates a new compatibility action bar advisor.
     *
     * @param wbAdvisor the workbench advisor
     * @param configurer the action bar configurer
     */
    public CompatibilityActionBarAdvisor(WorkbenchAdvisor wbAdvisor, IActionBarConfigurer configurer) {
        super(configurer);
        this.wbAdvisor = wbAdvisor;
    }
