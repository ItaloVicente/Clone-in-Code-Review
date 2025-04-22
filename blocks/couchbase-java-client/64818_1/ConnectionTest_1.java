    @Test
    public void shouldThrowBucketDoesNotExistExceptionForWrongBucketName() {
        Cluster cluster = CouchbaseCluster.create(TestProperties.seedNode());
        ClusterManager clusterManager = cluster.clusterManager(TestProperties.adminName(), TestProperties.adminPassword());
        assumeTrue("Server after 4.5.0 throw an IllegalArgumentException, see other test",
                clusterManager.info().getMinVersion().compareTo(new Version(4, 5, 0)) < 0);

        verifyException(cluster, BucketDoesNotExistException.class).openBucket("someWrongBucketName");
    }

    @Test
    public void shouldThrowInvalidPasswordExceptionForWrongBucketNameAfterWatson() {
