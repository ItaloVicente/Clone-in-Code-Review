    @Test
    public void shouldBootstrapWithBadHost() {
        Cluster cluster = CouchbaseCluster.create("badnode", TestProperties.seedNode());
        cluster.openBucket(TestProperties.bucket(), TestProperties.password());
    }

