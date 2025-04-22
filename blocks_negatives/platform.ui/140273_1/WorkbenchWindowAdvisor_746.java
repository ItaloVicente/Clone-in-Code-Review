    /**
     * Creates a new action bar advisor to configure the action bars of the window
     * via the given action bar configurer.
     * The default implementation returns a new instance of {@link ActionBarAdvisor}.
     *
     * @param configurer the action bar configurer for the window
     * @return the action bar advisor for the window
     */
    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        return new ActionBarAdvisor(configurer);
    }
