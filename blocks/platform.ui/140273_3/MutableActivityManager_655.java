	public MutableActivityManager(ITriggerPointAdvisor triggerPointAdvisor) {
		this(triggerPointAdvisor, new ExtensionActivityRegistry(Platform.getExtensionRegistry()));
	}

	public MutableActivityManager(ITriggerPointAdvisor triggerPointAdvisor, IActivityRegistry activityRegistry) {
		Assert.isNotNull(activityRegistry);
		Assert.isNotNull(triggerPointAdvisor);

		this.advisor = triggerPointAdvisor;
		this.activityRegistry = activityRegistry;

		this.activityRegistry.addActivityRegistryListener(activityRegistryListener);

		readRegistry(true);
	}
