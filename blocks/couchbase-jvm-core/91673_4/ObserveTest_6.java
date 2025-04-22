    private static final CoreEnvironment ENV = DefaultCoreEnvironment.create();

    @AfterClass
    public static void cleanup() {
        ENV.shutdown();
    }

