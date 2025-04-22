        Cluster cluster = null;
        try {
            cluster = CouchbaseCluster.create(env, TestProperties.seedNode());
            cluster.openBucket(TestProperties.bucket());
        } finally {
            if (cluster != null) {
                cluster.disconnect();
            }
            env.shutdown();
        }
