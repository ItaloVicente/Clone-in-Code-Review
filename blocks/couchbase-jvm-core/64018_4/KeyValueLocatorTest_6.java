    @Test
    public void shouldPickFastForwardIfAvailableAndRetry() throws Exception {
        Locator locator = new KeyValueLocator();

        NodeInfo nodeInfo1 = new DefaultNodeInfo("foo", "192.168.56.101:11210", Collections.EMPTY_MAP);
        NodeInfo nodeInfo2 = new DefaultNodeInfo("foo", "192.168.56.102:11210", Collections.EMPTY_MAP);
        List<Node> nodes = new ArrayList<Node>();
        Node node1Mock = mock(Node.class);
        when(node1Mock.hostname()).thenReturn(InetAddress.getByName("192.168.56.101"));
        Node node2Mock = mock(Node.class);
        when(node2Mock.hostname()).thenReturn(InetAddress.getByName("192.168.56.102"));
        nodes.addAll(Arrays.asList(node1Mock, node2Mock));

        ClusterConfig configMock = mock(ClusterConfig.class);
        CouchbaseBucketConfig bucketMock = mock(CouchbaseBucketConfig.class);
        when(configMock.bucketConfig("bucket")).thenReturn(bucketMock);
        when(bucketMock.nodes()).thenReturn(Arrays.asList(nodeInfo1, nodeInfo2));
        when(bucketMock.numberOfPartitions()).thenReturn(1024);
        when(bucketMock.nodeAtIndex(0)).thenReturn(nodeInfo1);
        when(bucketMock.nodeAtIndex(1)).thenReturn(nodeInfo2);
        when(bucketMock.hasFastForwardMap()).thenReturn(true);

        when(bucketMock.nodeIndexForMaster(656, false)).thenReturn((short) 0);
        when(bucketMock.nodeIndexForMaster(656, true)).thenReturn((short) 1);

        GetRequest getRequestMock = mock(GetRequest.class);
        when(getRequestMock.bucket()).thenReturn("bucket");
        when(getRequestMock.key()).thenReturn("key");
        when(getRequestMock.keyBytes()).thenReturn("key".getBytes(CharsetUtil.UTF_8));

        when(getRequestMock.retryCount()).thenReturn(0);
        locator.locateAndDispatch(getRequestMock, nodes, configMock, null, null);
        verify(node1Mock, times(1)).send(getRequestMock);
        verify(node2Mock, never()).send(getRequestMock);

        when(getRequestMock.retryCount()).thenReturn(1);
        locator.locateAndDispatch(getRequestMock, nodes, configMock, null, null);
        verify(node1Mock, times(1)).send(getRequestMock);
        verify(node2Mock, times(1)).send(getRequestMock);

        when(getRequestMock.retryCount()).thenReturn(5);
        locator.locateAndDispatch(getRequestMock, nodes, configMock, null, null);
        verify(node1Mock, times(1)).send(getRequestMock);
        verify(node2Mock, times(2)).send(getRequestMock);
    }

    @Test
    public void shouldPickCurrentIfNoFFMapAndRetry() throws Exception {
        Locator locator = new KeyValueLocator();

        NodeInfo nodeInfo1 = new DefaultNodeInfo("foo", "192.168.56.101:11210", Collections.EMPTY_MAP);
        NodeInfo nodeInfo2 = new DefaultNodeInfo("foo", "192.168.56.102:11210", Collections.EMPTY_MAP);
        List<Node> nodes = new ArrayList<Node>();
        Node node1Mock = mock(Node.class);
        when(node1Mock.hostname()).thenReturn(InetAddress.getByName("192.168.56.101"));
        Node node2Mock = mock(Node.class);
        when(node2Mock.hostname()).thenReturn(InetAddress.getByName("192.168.56.102"));
        nodes.addAll(Arrays.asList(node1Mock, node2Mock));

        ClusterConfig configMock = mock(ClusterConfig.class);
        CouchbaseBucketConfig bucketMock = mock(CouchbaseBucketConfig.class);
        when(configMock.bucketConfig("bucket")).thenReturn(bucketMock);
        when(bucketMock.nodes()).thenReturn(Arrays.asList(nodeInfo1, nodeInfo2));
        when(bucketMock.numberOfPartitions()).thenReturn(1024);
        when(bucketMock.nodeAtIndex(0)).thenReturn(nodeInfo1);
        when(bucketMock.nodeAtIndex(1)).thenReturn(nodeInfo2);
        when(bucketMock.hasFastForwardMap()).thenReturn(false);

        when(bucketMock.nodeIndexForMaster(656, false)).thenReturn((short) 0);
        when(bucketMock.nodeIndexForMaster(656, true)).thenReturn((short) 1);

        GetRequest getRequestMock = mock(GetRequest.class);
        when(getRequestMock.bucket()).thenReturn("bucket");
        when(getRequestMock.key()).thenReturn("key");
        when(getRequestMock.keyBytes()).thenReturn("key".getBytes(CharsetUtil.UTF_8));

        when(getRequestMock.retryCount()).thenReturn(0);
        locator.locateAndDispatch(getRequestMock, nodes, configMock, null, null);
        verify(node1Mock, times(1)).send(getRequestMock);
        verify(node2Mock, never()).send(getRequestMock);

        when(getRequestMock.retryCount()).thenReturn(1);
        locator.locateAndDispatch(getRequestMock, nodes, configMock, null, null);
        verify(node1Mock, times(2)).send(getRequestMock);
        verify(node2Mock, never()).send(getRequestMock);

        when(getRequestMock.retryCount()).thenReturn(5);
        locator.locateAndDispatch(getRequestMock, nodes, configMock, null, null);
        verify(node1Mock, times(3)).send(getRequestMock);
        verify(node2Mock, never()).send(getRequestMock);
    }

