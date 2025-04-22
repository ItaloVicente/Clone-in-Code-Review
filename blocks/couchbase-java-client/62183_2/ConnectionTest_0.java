    @Test
    public void shouldConnectAfterFailedAttempt() {
        Cluster cluster = CouchbaseCluster.create(TestProperties.seedNode());
        try {
            cluster.openBucket("protected");
        } catch (CouchbaseException e) {
            System.out.println(e);
        }
        cluster.credentialsManager().addBucketCredential("protected", "protected");
        Bucket b = cluster.openBucket("protected");
        assertNotNull(b);
    }

