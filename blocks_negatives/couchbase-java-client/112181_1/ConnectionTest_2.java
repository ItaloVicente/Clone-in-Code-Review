        Cluster cluster = null;
        try {
            cluster = CouchbaseCluster.create(env, TestProperties.seedNode());
            cluster.authenticate("foo", "bar");
        } finally {
            if (cluster != null) {
                cluster.disconnect();
            }
            env.shutdown();
        }
