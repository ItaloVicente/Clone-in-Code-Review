    /**
     * Create a new instance of this class using the platform extension registry.
     * @param triggerPointAdvisor
     */
    public MutableActivityManager(ITriggerPointAdvisor triggerPointAdvisor) {
        this(triggerPointAdvisor, new ExtensionActivityRegistry(Platform.getExtensionRegistry()));
    }

    /**
     * Create a new instance of this class using the provided registry.
     * @param triggerPointAdvisor
     *
     * @param activityRegistry the activity registry
     */
    public MutableActivityManager(ITriggerPointAdvisor triggerPointAdvisor, IActivityRegistry activityRegistry) {
        Assert.isNotNull(activityRegistry);
        Assert.isNotNull(triggerPointAdvisor);

        this.advisor = triggerPointAdvisor;
        this.activityRegistry = activityRegistry;

        this.activityRegistry
                .addActivityRegistryListener(activityRegistryListener);

        readRegistry(true);
    }
