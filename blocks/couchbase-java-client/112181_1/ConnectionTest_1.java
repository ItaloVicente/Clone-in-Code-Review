    public static Cluster cluster;
    public static CouchbaseEnvironment env;

    @After
    public void after() {
        if (cluster != null) {
            cluster.disconnect();
            cluster = null;
        }
        if (env != null) {
            env.shutdown();
            env = null;
        }
    }

