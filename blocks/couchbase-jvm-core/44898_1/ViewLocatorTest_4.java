    @Test
    public void shouldSkipNodeWithoutPartition() throws Exception {
        Locator locator = new ViewLocator();

        ViewQueryRequest request = mock(ViewQueryRequest.class);
        when(request.bucket()).thenReturn("default");
        ClusterConfig configMock = mock(ClusterConfig.class);
        CouchbaseBucketConfig bucketConfigMock = mock(CouchbaseBucketConfig.class);
        when(bucketConfigMock.hasPrimaryPartitionsOnNode(InetAddress.getByName("192.168.56.101"))).thenReturn(false);
        when(bucketConfigMock.hasPrimaryPartitionsOnNode(InetAddress.getByName("192.168.56.102"))).thenReturn(true);

        when(configMock.bucketConfig("default")).thenReturn(bucketConfigMock);
        Set<Node> nodes = new LinkedHashSet<Node>();
        Node node1Mock = mock(Node.class);
        when(node1Mock.hostname()).thenReturn(InetAddress.getByName("192.168.56.101"));
        Node node2Mock = mock(Node.class);
        when(node2Mock.hostname()).thenReturn(InetAddress.getByName("192.168.56.102"));
        nodes.addAll(Arrays.asList(node1Mock, node2Mock));

        Node[] located = locator.locate(request, nodes, configMock);
        assertEquals(0, located.length);

        located = locator.locate(request, nodes, configMock);
        assertEquals(1, located.length);
        InetAddress foundSecond = located[0].hostname();

        located = locator.locate(request, nodes, configMock);
        assertEquals(0, located.length);

        assertEquals(foundSecond, InetAddress.getByName("192.168.56.102"));
    }

    @Test
    public void shouldFailWhenUsedAgainstMemcacheBucket() {
        Locator locator = new ViewLocator();

        ClusterConfig config = mock(ClusterConfig.class);
        when(config.bucketConfig("default")).thenReturn(mock(MemcachedBucketConfig.class));

        CouchbaseRequest request = mock(ViewQueryRequest.class);
        Subject<CouchbaseResponse, CouchbaseResponse> response = AsyncSubject.create();
        when(request.bucket()).thenReturn("default");
        when(request.observable()).thenReturn(response);

        TestSubscriber<CouchbaseResponse> subscriber = new TestSubscriber<CouchbaseResponse>();
        response.subscribe(subscriber);

        Node[] located = locator.locate(request, Collections.<Node>emptySet(), config);

        assertNull(located);

        subscriber.awaitTerminalEvent(1, TimeUnit.SECONDS);
        List<Throwable> errors = subscriber.getOnErrorEvents();
        assertEquals(1, errors.size());
        assertTrue(errors.get(0) instanceof ServiceNotAvailableException);
    }

