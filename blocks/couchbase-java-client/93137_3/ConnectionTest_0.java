    @Test
    public void shouldProvideClusterInfoWithBadHostInBootstrapList() {
        Cluster cluster = CouchbaseCluster.create("1.1.1.1", "2.2.2.2", "3.3.3.3", "4.4.4.4", "127.0.0.1");
        cluster.authenticate(TestProperties.adminName(), TestProperties.adminPassword());
        cluster.clusterManager().info();
    }

