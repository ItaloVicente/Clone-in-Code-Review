    /**
     * Create a new instance of this class using the provided registry.
     * @param triggerPointAdvisor
     *
     * @param activityRegistry the activity registry
     */
    public MutableActivityManager(ITriggerPointAdvisor triggerPointAdvisor, IActivityRegistry activityRegistry) {
        Assert.isNotNull(activityRegistry);
        Assert.isNotNull(triggerPointAdvisor);
