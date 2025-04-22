
    @Test
    public void shouldUsePortsFromConfigIfPresentWithAlternateNetwork() {
        ClusterFacade cluster = mock(ClusterFacade.class);
        HttpLoader loader = new HttpLoader(cluster, environment);

        ClusterConfig clusterConfig = new DefaultClusterConfig();

        String raw = Resources.read("config_with_external.json", DefaultCouchbaseBucketConfigTest.class);
        CouchbaseBucketConfig config = (CouchbaseBucketConfig)
            BucketConfigParser.parse(raw, mock(CoreEnvironment.class), "127.0.0.1");

        config.useAlternateNetwork("external");

        clusterConfig.setBucketConfig("foo", config);

        when(cluster.send(any(GetClusterConfigRequest.class))).thenReturn(Observable.just(
            (CouchbaseResponse) new GetClusterConfigResponse(clusterConfig, ResponseStatus.SUCCESS)
        ));

        assertEquals(32790, loader.port("172.17.0.2"));
    }

    @Test
    public void shouldUsePortsFromConfigIfPresentWithoutAlternateNetwork() {
        ClusterFacade cluster = mock(ClusterFacade.class);
        HttpLoader loader = new HttpLoader(cluster, environment);

        ClusterConfig clusterConfig = new DefaultClusterConfig();

        String raw = Resources.read("config_with_external.json", DefaultCouchbaseBucketConfigTest.class);
        CouchbaseBucketConfig config = (CouchbaseBucketConfig)
            BucketConfigParser.parse(raw, mock(CoreEnvironment.class), "127.0.0.1");

        clusterConfig.setBucketConfig("foo", config);

        when(cluster.send(any(GetClusterConfigRequest.class))).thenReturn(Observable.just(
            (CouchbaseResponse) new GetClusterConfigResponse(clusterConfig, ResponseStatus.SUCCESS)
        ));

        assertEquals(8091, loader.port("172.17.0.2"));
    }
