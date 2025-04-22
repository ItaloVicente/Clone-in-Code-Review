    @Test
    public void shouldProvideClusterInfoWithBadHostInBootstrapList() {
        Cluster cluster = CouchbaseCluster.create(TestProperties.seedNode(), "216.58.194.174");
        cluster.authenticate("Administrator", "password");
        cluster.clusterManager().info();
    }

