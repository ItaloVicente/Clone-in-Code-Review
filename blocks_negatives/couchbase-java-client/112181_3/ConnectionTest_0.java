        Cluster cluster = null;
        try {
            cluster = CouchbaseCluster.create(TestProperties.seedNode());
            cluster.authenticate(CertAuthenticator.INSTANCE);
        } finally {
            if (cluster != null) {
                cluster.disconnect();
            }
        }
