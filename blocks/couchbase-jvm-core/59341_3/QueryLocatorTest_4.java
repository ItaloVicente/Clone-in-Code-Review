    @Test
    public void shouldSkipNodeWithoutServiceEnabled() throws Exception {
        Locator locator = new QueryLocator();

        GenericQueryRequest request = mock(GenericQueryRequest.class);
        when(request.bucket()).thenReturn("default");
        ClusterConfig configMock = mock(ClusterConfig.class);

        List<Node> nodes = new ArrayList<Node>();
        Node node1Mock = mock(Node.class);
        when(node1Mock.hostname()).thenReturn(InetAddress.getByName("192.168.56.101"));
        when(node1Mock.serviceEnabled(ServiceType.QUERY)).thenReturn(false);
        Node node2Mock = mock(Node.class);
        when(node2Mock.hostname()).thenReturn(InetAddress.getByName("192.168.56.102"));
        when(node2Mock.serviceEnabled(ServiceType.QUERY)).thenReturn(true);
        Node node3Mock = mock(Node.class);
        when(node3Mock.hostname()).thenReturn(InetAddress.getByName("192.168.56.103"));
        when(node3Mock.serviceEnabled(ServiceType.QUERY)).thenReturn(false);
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

        assertEquals(foundFirst, InetAddress.getByName("192.168.56.102"));
        assertEquals(foundSecond, InetAddress.getByName("192.168.56.102"));
        assertEquals(foundThird, InetAddress.getByName("192.168.56.102"));
        assertEquals(foundFourth, InetAddress.getByName("192.168.56.102"));
    }

