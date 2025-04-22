    private static final CoreContext ctx = new CoreContext(environment, null);

    @AfterClass
    public static void after() {
        environment.shutdown();
    }
