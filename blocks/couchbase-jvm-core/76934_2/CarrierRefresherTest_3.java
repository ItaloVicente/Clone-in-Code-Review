
    @Test
    public void shouldShiftNodeList() {
        ClusterFacade cluster = mock(ClusterFacade.class);
        CarrierRefresher refresher = new CarrierRefresher(ENVIRONMENT, cluster);

        List<Integer> list = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4));
        refresher.shiftNodeList(list);
        assertEquals(Arrays.asList(0, 1, 2, 3, 4), list); // shift by 0

        list = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4));
        refresher.shiftNodeList(list);
        assertEquals(Arrays.asList(1, 2, 3, 4, 0), list); // shift by 1

        list = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4));
        refresher.shiftNodeList(list);
        assertEquals(Arrays.asList(2, 3, 4, 0, 1), list); // shift by 2

        list = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4));
        refresher.shiftNodeList(list);
        assertEquals(Arrays.asList(3, 4, 0, 1, 2), list); // shift by 3

        list = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4));
        refresher.shiftNodeList(list);
        assertEquals(Arrays.asList(4, 0, 1, 2, 3), list); // shift by 4

        list = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4));
        refresher.shiftNodeList(list);
        assertEquals(Arrays.asList(0, 1, 2, 3, 4), list); // shift by 0

        list = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4));
        refresher.shiftNodeList(list);
        assertEquals(Arrays.asList(1, 2, 3, 4, 0), list); // shift by 1
    }

    @Test
    public void shouldUseShiftedNodeListOnTaintedPolling() {

    }

    @Test
    public void shouldUseShiftedNodeListOnRefreshPolling() throws Exception {
        ClusterFacade cluster = mock(ClusterFacade.class);
        final CarrierRefresher refresher = new CarrierRefresher(ENVIRONMENT, cluster);
        refresher.registerBucket("bucket", "");
        ConfigurationProvider provider = mock(ConfigurationProvider.class);
        refresher.provider(provider);

        ClusterConfig clusterConfig = mock(ClusterConfig.class);
        BucketConfig bucketConfig = mock(BucketConfig.class);
        when(bucketConfig.name()).thenReturn("bucket");
        List<NodeInfo> nodeInfos = new ArrayList<NodeInfo>();

        Map<String, Integer> ports = new HashMap<String, Integer>();
        ports.put("direct", 11210);
        nodeInfos.add(new DefaultNodeInfo(null, "1.2.3.4:8091", ports));
        nodeInfos.add(new DefaultNodeInfo(null, "2.3.4.5:8091", ports));
        when(bucketConfig.nodes()).thenReturn(nodeInfos);
        Map<String, BucketConfig> bucketConfigs = new HashMap<String, BucketConfig>();
        bucketConfigs.put("bucket", bucketConfig);

        when(clusterConfig.bucketConfigs()).thenReturn(bucketConfigs);

        final List<String> nodesRequested = Collections.synchronizedList(new ArrayList<String>());
        when(cluster.send(any(GetBucketConfigRequest.class))).thenAnswer(new Answer<Observable<GetBucketConfigResponse>>() {
            @Override
            public Observable<GetBucketConfigResponse> answer(InvocationOnMock invocation) throws Throwable {
                GetBucketConfigRequest request = (GetBucketConfigRequest) invocation.getArguments()[0];
                nodesRequested.add(request.hostname().getHostAddress());
                return Observable.just(
                        new GetBucketConfigResponse(
                                ResponseStatus.SUCCESS, KeyValueStatus.SUCCESS.code(),
                                "bucket",
                                Unpooled.copiedBuffer("{\"config\": true}", CharsetUtil.UTF_8),
                                InetAddress.getLocalHost()
                        )
                );
            }
        });

        refresher.refresh(clusterConfig);
        Thread.sleep(500);
        refresher.refresh(clusterConfig);
        Thread.sleep(500);
        refresher.refresh(clusterConfig);
        Thread.sleep(500);
        refresher.refresh(clusterConfig);
        Thread.sleep(500);

        verify(provider, times(4)).proposeBucketConfig("bucket", "{\"config\": true}");
        assertEquals("1.2.3.4", nodesRequested.get(0));
        assertEquals("2.3.4.5", nodesRequested.get(1));
        assertEquals("1.2.3.4", nodesRequested.get(2));
        assertEquals("2.3.4.5", nodesRequested.get(3));
    }
