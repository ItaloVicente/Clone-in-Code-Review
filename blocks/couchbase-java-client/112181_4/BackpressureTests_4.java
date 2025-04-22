    @BeforeClass
    public static void before() {
        ENV = DefaultCouchbaseEnvironment.create();
    }
    @AfterClass
    public static void after() {
        ENV.shutdown();
    }

