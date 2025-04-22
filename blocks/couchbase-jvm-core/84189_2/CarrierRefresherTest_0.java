
    @Test
    public void shouldEnforcePollFloorPerBucket() throws Exception {
        CoreEnvironment env = mock(CoreEnvironment.class);
        when(env.sslEnabled()).thenReturn(false);
        when(env.scheduler()).thenReturn(new CoreScheduler(4));
        ClusterFacade clusterFacade = mock(ClusterFacade.class);
        when(clusterFacade.send(any(CouchbaseRequest.class))).thenReturn(Observable.<CouchbaseResponse>never());
        CarrierRefresher refresher = new CarrierRefresher(env, clusterFacade);
        refresher.registerBucket("bucket1", "password");
        refresher.registerBucket("bucket2", "password");

        List<NodeInfo> nodeInfos = new ArrayList<NodeInfo>();
        Map<String, Integer> ports = new HashMap<String, Integer>();
        ports.put("direct", 11210);
        nodeInfos.add(new DefaultNodeInfo(null, "1.2.3.4:8091", ports));
        nodeInfos.add(new DefaultNodeInfo(null, "2.3.4.5:8091", ports));


        ClusterConfig cc = mock(ClusterConfig.class);
        Map<String, BucketConfig> bcs = new HashMap<String, BucketConfig>();
        BucketConfig b1 = mock(BucketConfig.class);
        when(b1.name()).thenReturn("bucket1");
        when(b1.nodes()).thenReturn(nodeInfos);
        BucketConfig b2 = mock(BucketConfig.class);
        when(b2.name()).thenReturn("bucket2");
        when(b2.nodes()).thenReturn(nodeInfos);
        bcs.put("bucket1", b1);
        bcs.put("bucket2", b2);
        when(cc.bucketConfigs()).thenReturn(bcs);

        refresher.refresh(cc);

        Thread.sleep(500);

        verify(clusterFacade, times(2)).send(any(CouchbaseRequest.class));
    }
