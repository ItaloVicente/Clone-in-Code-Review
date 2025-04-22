    public Cluster cluster;
    public CouchbaseEnvironment env;

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

