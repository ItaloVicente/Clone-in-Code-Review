    /**
     * Returns the suite. This is required to use the JUnit Launcher.
     */
    public static Test suite() {
        return new ActivitiesPerformanceSuite();
    }

    /**
     *
     */
    public ActivitiesPerformanceSuite() {
        super();
        addTest(new GenerateIdentifiersTest(10000));
    }
