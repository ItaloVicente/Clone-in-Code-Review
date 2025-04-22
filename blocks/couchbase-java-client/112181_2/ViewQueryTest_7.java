    private static CouchbaseEnvironment env;

    @After
    public void after() {
        if (env != null) {
            env.shutdown();
        }
    }

