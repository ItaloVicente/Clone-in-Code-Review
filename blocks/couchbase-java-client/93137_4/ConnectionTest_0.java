    @Test
    public void shouldProvideClusterInfoWithBadHostInBootstrapList() {
        Cluster cluster = CouchbaseCluster.create("1.1.1.1", "2.2.2.2", "3.3.3.3", "4.4.4.4", TestProperties.seedNode());
        cluster.authenticate(TestProperties.adminName(), TestProperties.adminPassword());
        cluster.clusterManager().info();
    }

