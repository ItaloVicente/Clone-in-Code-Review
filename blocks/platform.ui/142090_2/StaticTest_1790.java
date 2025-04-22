	public StaticTest(String testName) {
		super(testName);
		activityManager = PlatformUI.getWorkbench().getActivitySupport()
				.getActivityManager();
		populateIds();
	}
