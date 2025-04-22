    @Test
    public void shouldPickTheRightNodeForGetBucketConfigRequest() throws Exception {
        Locator locator = new KeyValueLocator();

        Set<Node> nodes = new LinkedHashSet<Node>();
        Node node1Mock = mock(Node.class);
        when(node1Mock.hostname()).thenReturn(InetAddress.getByName("192.168.56.101"));
        when(node1Mock.isState(LifecycleState.CONNECTED)).thenReturn(true);
        Node node2Mock = mock(Node.class);
        when(node2Mock.hostname()).thenReturn(InetAddress.getByName("192.168.56.102"));
        when(node2Mock.isState(LifecycleState.CONNECTED)).thenReturn(true);
        nodes.addAll(Arrays.asList(node1Mock, node2Mock));

        GetBucketConfigRequest requestMock = mock(GetBucketConfigRequest.class);
        when(requestMock.hostname()).thenReturn(InetAddress.getByName("192.168.56.102"));

        Node[] foundNodes = locator.locate(requestMock, nodes, mock(ClusterConfig.class));
        assertEquals(node2Mock.hostname(), foundNodes[0].hostname());
    }

