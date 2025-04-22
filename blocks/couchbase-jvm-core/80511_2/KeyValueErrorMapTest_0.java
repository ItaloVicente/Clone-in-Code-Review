    @Before
    public void before() throws Exception {
        assumeTrue(
            "Ignoring because extended error not enabled",
            Boolean.parseBoolean(System.getProperty("com.couchbase.xerrorEnabled", "false"))
        );
    }

