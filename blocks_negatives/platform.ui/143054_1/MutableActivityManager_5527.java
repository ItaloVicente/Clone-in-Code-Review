    /**
     * Create a new instance of this class using the platform extension registry.
     * @param triggerPointAdvisor
     */
    public MutableActivityManager(ITriggerPointAdvisor triggerPointAdvisor) {
        this(triggerPointAdvisor, new ExtensionActivityRegistry(Platform.getExtensionRegistry()));
    }
