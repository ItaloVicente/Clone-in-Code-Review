	public DynamicTest(String testName) {
		super(testName);
		fixedModelRegistry = new DynamicModelActivityRegistry();
		activityManager = new MutableActivityManager(new WorkbenchTriggerPointAdvisor(), fixedModelRegistry);
		listenerType = -1;
	}
