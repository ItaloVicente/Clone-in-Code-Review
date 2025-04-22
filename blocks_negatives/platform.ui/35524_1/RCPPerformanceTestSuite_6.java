    /**
     * Returns the suite. This is required to use the JUnit Launcher.
     */
    public static Test suite() {
    	return new RCPPerformanceTestSetup(new RCPPerformanceTestSuite());
    }

    /**
     * Construct the test suite.
     */
    public RCPPerformanceTestSuite() {
        addTestSuite(PlatformUIPerfTest.class);
        addTestSuite(EmptyWorkbenchPerfTest.class);
    }
