    /**
     * Creates a new action bar advisor to configure a workbench
     * window's action bars via the given action bar configurer.
     *
     * @param configurer the action bar configurer
     */
    public ActionBarAdvisor(IActionBarConfigurer configurer) {
        Assert.isNotNull(configurer);
        actionBarConfigurer = configurer;
    }
