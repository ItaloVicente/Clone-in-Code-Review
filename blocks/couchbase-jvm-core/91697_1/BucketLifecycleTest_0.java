    private static final CoreEnvironment ENV = DefaultCoreEnvironment.create();

    @AfterClass
    public static final void cleanup() {
        ENV.shutdown();
    }

