    @Test
    public void shouldFallbackToNextOnRefreshWhenFirstFails() throws Exception {
        ClusterFacade cluster = mock(ClusterFacade.class);
        CarrierRefresher refresher = new CarrierRefresher(ENVIRONMENT, cluster);
        refresher.registerBucket("bucket", "");
        ConfigurationProvider provider = mock(ConfigurationProvider.class);
        refresher.provider(provider);

        ClusterConfig clusterConfig = mock(ClusterConfig.class);
        BucketConfig bucketConfig = mock(BucketConfig.class);
        when(bucketConfig.name()).thenReturn("bucket");
        List<NodeInfo> nodeInfos = new ArrayList<NodeInfo>();
        nodeInfos.add(new DefaultNodeInfo(null, "1.2.3.4:8091", new HashMap<String, Integer>()));
        nodeInfos.add(new DefaultNodeInfo(null, "2.3.4.5:8091", new HashMap<String, Integer>()));
        when(bucketConfig.nodes()).thenReturn(nodeInfos);
        Map<String, BucketConfig> bucketConfigs = new HashMap<String, BucketConfig>();
        bucketConfigs.put("bucket", bucketConfig);

        when(clusterConfig.bucketConfigs()).thenReturn(bucketConfigs);

        ByteBuf content = Unpooled.copiedBuffer("{\"config\": true}", CharsetUtil.UTF_8);
        Observable<CouchbaseResponse> goodResponse = Observable.just(
            (CouchbaseResponse) new GetBucketConfigResponse(
                ResponseStatus.SUCCESS,
                "bucket",
                content,
                InetAddress.getByName("1.2.3.4")
            )
        );
        Observable<CouchbaseResponse> badResponse = Observable.error(new CouchbaseException("Woops.."));
        when(cluster.send(any(GetBucketConfigRequest.class))).thenReturn(badResponse, goodResponse);

        refresher.refresh(clusterConfig);

        Thread.sleep(200);

        verify(provider, times(1)).proposeBucketConfig("bucket", "{\"config\": true}");
        assertEquals(0, content.refCnt());
    }

    @Test
    public void shouldFallbackToNextOnPollWhenFirstFails() throws Exception {
        ClusterFacade cluster = mock(ClusterFacade.class);
        ConfigurationProvider provider = mock(ConfigurationProvider.class);
        BucketConfig config = mock(BucketConfig.class);

        CarrierRefresher refresher = new CarrierRefresher(ENVIRONMENT, cluster);
        refresher.provider(provider);

        when(config.name()).thenReturn("bucket");
        List<NodeInfo> nodeInfos = new ArrayList<NodeInfo>();
        nodeInfos.add(new DefaultNodeInfo(null, "1.2.3.4:8091", new HashMap<String, Integer>()));
        nodeInfos.add(new DefaultNodeInfo(null, "2.3.4.5:8091", new HashMap<String, Integer>()));
        when(config.nodes()).thenReturn(nodeInfos);

        ByteBuf content = Unpooled.copiedBuffer("{\"config\": true}", CharsetUtil.UTF_8);
        Observable<CouchbaseResponse> goodResponse = Observable.just((CouchbaseResponse) new GetBucketConfigResponse(
            ResponseStatus.SUCCESS,
            "bucket",
            content,
            InetAddress.getByName("1.2.3.4")
        ));
        Observable<CouchbaseResponse> badResponse = Observable.error(new CouchbaseException("Failure"));
        when(cluster.send(any(GetBucketConfigRequest.class))).thenReturn(badResponse, goodResponse);
        refresher.markTainted(config);

        Thread.sleep(1500);

        verify(provider, times(1)).proposeBucketConfig("bucket", "{\"config\": true}");
        assertEquals(0, content.refCnt());
    }


