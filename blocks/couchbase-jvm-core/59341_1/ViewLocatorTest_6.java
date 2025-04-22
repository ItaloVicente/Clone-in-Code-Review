        assertEquals(foundThird, InetAddress.getByName("192.168.56.102"));
    }

    @Test
    public void shouldSkipNodeWithoutServiceEnabled() throws Exception {
        Locator locator = new ViewLocator();

        ViewQueryRequest request = mock(ViewQueryRequest.class);
        when(request.bucket()).thenReturn("default");
        ClusterConfig configMock = mock(ClusterConfig.class);
        CouchbaseBucketConfig bucketConfigMock = mock(CouchbaseBucketConfig.class);
        when(bucketConfigMock.hasPrimaryPartitionsOnNode(InetAddress.getByName("192.168.56.101"))).thenReturn(true);
        when(bucketConfigMock.hasPrimaryPartitionsOnNode(InetAddress.getByName("192.168.56.102"))).thenReturn(false);
        when(bucketConfigMock.hasPrimaryPartitionsOnNode(InetAddress.getByName("192.168.56.103"))).thenReturn(true);

        when(configMock.bucketConfig("default")).thenReturn(bucketConfigMock);
        List<Node> nodes = new ArrayList<Node>();
        Node node1Mock = mock(Node.class);
        when(node1Mock.hostname()).thenReturn(InetAddress.getByName("192.168.56.101"));
        when(node1Mock.serviceEnabled(ServiceType.VIEW)).thenReturn(true);
        Node node2Mock = mock(Node.class);
        when(node2Mock.hostname()).thenReturn(InetAddress.getByName("192.168.56.102"));
        when(node2Mock.serviceEnabled(ServiceType.VIEW)).thenReturn(false);
        Node node3Mock = mock(Node.class);
        when(node3Mock.hostname()).thenReturn(InetAddress.getByName("192.168.56.103"));
        when(node3Mock.serviceEnabled(ServiceType.VIEW)).thenReturn(true);
        nodes.addAll(Arrays.asList(node1Mock, node2Mock, node3Mock));

        Node[] located = locator.locate(request, nodes, configMock);
        assertEquals(1, located.length);
        InetAddress foundFirst = located[0].hostname();

        located = locator.locate(request, nodes, configMock);
        assertEquals(1, located.length);
        InetAddress foundSecond = located[0].hostname();

        located = locator.locate(request, nodes, configMock);
        assertEquals(1, located.length);
        InetAddress foundThird = located[0].hostname();

        located = locator.locate(request, nodes, configMock);
        assertEquals(1, located.length);
        InetAddress foundFourth = located[0].hostname();

        assertEquals(foundFirst, InetAddress.getByName("192.168.56.101"));
        assertEquals(foundSecond, InetAddress.getByName("192.168.56.103"));
        assertEquals(foundThird, InetAddress.getByName("192.168.56.103"));
        assertEquals(foundFourth, InetAddress.getByName("192.168.56.101"));
