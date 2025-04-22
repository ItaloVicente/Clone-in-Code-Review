        Cluster cluster = null;
        try {
            cluster = CouchbaseCluster.create(env, TestProperties.seedNode());
            cluster.authenticate(CertAuthenticator.INSTANCE);
            cluster.openBucket(TestProperties.bucket(), "password");
        } finally {
            if (cluster != null) {
                cluster.disconnect();
            }
            env.shutdown();
        }
