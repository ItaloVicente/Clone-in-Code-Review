    private static final CouchbaseEnvironment ENV = DefaultCouchbaseEnvironment.create();

    @AfterClass
    public static void tearDown() {
        ENV.shutdown();
    }

