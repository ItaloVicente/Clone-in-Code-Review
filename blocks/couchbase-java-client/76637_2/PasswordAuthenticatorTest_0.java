    @Test
    public void shouldOpenBucketWithShortcutOverload() {
        Cluster cluster = CouchbaseCluster.create(ctx.seedNode());
        cluster.authenticate(username, password);
        cluster.openBucket(ctx.bucketName());
        cluster.disconnect();
    }

