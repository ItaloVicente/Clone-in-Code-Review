
    @Test(expected = ConfigurationException.class)
    public void shouldThrowConfigurationExceptionForWrongBucketName() {
        Cluster cluster = CouchbaseCluster.create(TestProperties.seedNode());
        cluster.openBucket("someWrongBucketName");
    }

    @Test(expected = ConfigurationException.class)
    public void shouldThrowConfigurationExceptionForWrongBucketPassword() {
        Cluster cluster = CouchbaseCluster.create(TestProperties.seedNode());
        cluster.openBucket(TestProperties.bucket(), "completelyWrongPassword");
    }
