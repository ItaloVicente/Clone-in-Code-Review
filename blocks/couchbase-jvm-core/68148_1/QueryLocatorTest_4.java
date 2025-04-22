    @Test
    public void shouldDistributeFairlyUnderMDS() throws Exception {
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
        when(node2Mock.serviceEnabled(ServiceType.QUERY)).thenReturn(false);
        Node node3Mock = mock(Node.class);
        when(node3Mock.hostname()).thenReturn(InetAddress.getByName("192.168.56.103"));
        when(node3Mock.serviceEnabled(ServiceType.QUERY)).thenReturn(true);
        Node node4Mock = mock(Node.class);
        when(node4Mock.hostname()).thenReturn(InetAddress.getByName("192.168.56.104"));
        when(node4Mock.serviceEnabled(ServiceType.QUERY)).thenReturn(true);
        nodes.addAll(Arrays.asList(node1Mock, node2Mock, node3Mock, node4Mock));


        locator.locateAndDispatch(request, nodes, configMock, null, null);
        verify(node1Mock, never()).send(request);
        verify(node2Mock, never()).send(request);
        verify(node3Mock, times(1)).send(request);
        verify(node4Mock, never()).send(request);


        locator.locateAndDispatch(request, nodes, configMock, null, null);
        verify(node1Mock, never()).send(request);
        verify(node2Mock, never()).send(request);
        verify(node3Mock, times(1)).send(request);
        verify(node4Mock, times(1)).send(request);

        locator.locateAndDispatch(request, nodes, configMock, null, null);
        verify(node1Mock, never()).send(request);
        verify(node2Mock, never()).send(request);
        verify(node3Mock, times(2)).send(request);
        verify(node4Mock, times(1)).send(request);

        locator.locateAndDispatch(request, nodes, configMock, null, null);
        verify(node1Mock, never()).send(request);
        verify(node2Mock, never()).send(request);
        verify(node3Mock, times(2)).send(request);
        verify(node4Mock, times(2)).send(request);
    }

