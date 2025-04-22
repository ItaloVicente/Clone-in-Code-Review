    /**
     * Constructor.
     *
     * @param testName
     *            Test's name.
     */
    public StaticTest(String testName) {
        super(testName);
        activityManager = PlatformUI.getWorkbench().getActivitySupport()
                .getActivityManager();
        populateIds();
    }
