    private CouchbaseEnvironment env;
    private CoreEnvironment coreEnv;

    @After
    public void afterEach() {
        if (env != null) {
            env.shutdown();
        }
        if (coreEnv != null) {
            coreEnv.shutdown();
        }
    }

