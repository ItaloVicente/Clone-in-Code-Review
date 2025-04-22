        Cluster cluster = CouchbaseCluster.create("x.y.z", TestProperties.seedNode());
        cluster.authenticate(TestProperties.adminName(), TestProperties.adminPassword());
        cluster.clusterManager().info();
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotCheckReverseLookupWhenDNSSRVEnabled() throws Exception {
        Cluster cluster = CouchbaseCluster.create(DefaultCouchbaseEnvironment.builder().dnsSrvEnabled(true).build(), "x.y.z");
