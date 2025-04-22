    @Test(expected = InvalidPasswordException.class)
    public void shouldFailOnWrongUser() {
        ClusterManager manager = couchbaseCluster.clusterManager("invalidUser", "");
        manager.getBuckets();
    }

    @Test(expected = InvalidPasswordException.class)
    public void shouldFailOnWrongPassword() {
        ClusterManager manager = couchbaseCluster.clusterManager(TestProperties.adminName(), "foobar3423$$");
        manager.getBuckets();
    }

