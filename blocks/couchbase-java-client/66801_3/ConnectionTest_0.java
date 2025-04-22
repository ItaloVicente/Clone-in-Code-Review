
    @Test
    public void shouldOpenBucketAfterFailedAttempt() {
        Cluster cluster = CouchbaseCluster.create(TestProperties.seedNode());
        Bucket bucket0 = cluster.openBucket("travel-sample", "");
        try {
            Bucket bucket1 = cluster.openBucket("protected", "bad");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bucket bucket2 = cluster.openBucket("default", "");
        assertFalse(bucket2.isClosed());
        bucket2.get("toto");
    }
