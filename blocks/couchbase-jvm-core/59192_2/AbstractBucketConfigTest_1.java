    @Test
    public void shouldCheckIfNodeServiceIsEnabled() throws Exception {
        List<NodeInfo> nodeInfos = new ArrayList<NodeInfo>();

        Map<ServiceType, Integer> directKv = new HashMap<ServiceType, Integer>();
        Map<ServiceType, Integer> directQuery = new HashMap<ServiceType, Integer>();

        directKv.put(ServiceType.BINARY, 11211);
        directQuery.put(ServiceType.QUERY, 8093);

        InetAddress node1 = InetAddress.getByName("1.2.3.4");
        InetAddress node2 = InetAddress.getByName("1.2.3.5");

        nodeInfos.add(new DefaultNodeInfo(node1, directKv, new HashMap<ServiceType, Integer>()));
        nodeInfos.add(new DefaultNodeInfo(node2, directQuery, new HashMap<ServiceType, Integer>()));

        BucketConfig bc = new SampleBucketConfig(nodeInfos, null);

        assertTrue(bc.serviceEnabled(ServiceType.BINARY));
        assertTrue(bc.serviceEnabled(ServiceType.QUERY));
        assertFalse(bc.serviceEnabled(ServiceType.CONFIG));

        assertTrue(bc.serviceEnabled(ServiceType.BINARY, node1));
        assertTrue(bc.serviceEnabled(ServiceType.QUERY, node2));
        assertFalse(bc.serviceEnabled(ServiceType.QUERY, node1));
        assertFalse(bc.serviceEnabled(ServiceType.BINARY, node2));
        assertFalse(bc.serviceEnabled(ServiceType.CONFIG, node1));
        assertFalse(bc.serviceEnabled(ServiceType.CONFIG, node2));

        assertFalse(bc.serviceEnabled(ServiceType.DCP, InetAddress.getByName("1.1.1.99")));
    }

