
    @Test
    public void shouldNotPollBelowFloor() throws Exception {
        ClusterFacade cluster = mock(ClusterFacade.class);
        CarrierRefresher refresher = new CarrierRefresher(ENVIRONMENT, cluster);
        refresher.registerBucket("bucket", "");
        ConfigurationProvider provider = mock(ConfigurationProvider.class);
        refresher.provider(provider);

        ClusterConfig clusterConfig = mock(ClusterConfig.class);
        BucketConfig bucketConfig = mock(BucketConfig.class);
        when(bucketConfig.name()).thenReturn("bucket");
        List<NodeInfo> nodeInfos = new ArrayList<NodeInfo>();
        Map<String, Integer> ports = new HashMap<String, Integer>();
        ports.put("direct", 11210);
        nodeInfos.add(new DefaultNodeInfo(null, "localhost:8091", ports));
        when(bucketConfig.nodes()).thenReturn(nodeInfos);
        Map<String, BucketConfig> bucketConfigs = new HashMap<String, BucketConfig>();
        bucketConfigs.put("bucket", bucketConfig);

        when(clusterConfig.bucketConfigs()).thenReturn(bucketConfigs);

        final List<Long> invocationTimings = Collections.synchronizedList(new ArrayList<Long>());
        when(cluster.send(any(GetBucketConfigRequest.class))).thenAnswer(new Answer<Observable<CouchbaseResponse>>() {
            @Override
            public Observable<CouchbaseResponse> answer(InvocationOnMock invocation) throws Throwable {
                invocationTimings.add(System.nanoTime());
                return Observable.just(
                        (CouchbaseResponse) new GetBucketConfigResponse(
                                ResponseStatus.SUCCESS, KeyValueStatus.SUCCESS.code(),
                                "bucket",
                                Unpooled.copiedBuffer("{\"config\": true}", CharsetUtil.UTF_8),
                                InetAddress.getByName("localhost")
                        )
                );
            }
        });

        int attempts = 400;
        for (int i = 0; i < attempts; i++) {
            refresher.refresh(clusterConfig);
            Thread.sleep(2);
        }

        Thread.sleep(200);

        long lastCall = invocationTimings.get(0);
        int good = 0;
        int bad = 0;
        for (int i = 1; i < invocationTimings.size(); i++) {
            if ((invocationTimings.get(i) - lastCall) >= CarrierRefresher.POLL_FLOOR_NS) {
                good++;
            } else {
                bad++;
            }
            lastCall = invocationTimings.get(i);
        }
        assertTrue(good > bad);
    }
