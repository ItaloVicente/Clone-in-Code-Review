
    @Test
    public void shouldUseExposeAlternateIfSet() {
        Map<String, Integer> ports = new HashMap<String, Integer>();
        ports.put("direct", 1234);
        Map<String, Integer> externalPorts = new HashMap<String, Integer>();
        externalPorts.put("kv", 5678);

        Map<AlternateAddressType, AlternateAddress> alternate = new HashMap<AlternateAddressType, AlternateAddress>();
        alternate.put(AlternateAddressType.EXTERNAL, new DefaultAlternateAddress("5.6.7.8", externalPorts));

        NodeInfo nodeInfo = new DefaultNodeInfo(null, "1.2.3.4:8091", ports, alternate);
        nodeInfo.useAlternateNetwork(true);
        assertEquals("5.6.7.8", nodeInfo.rawHostname());
        assertEquals(NetworkAddress.create("5.6.7.8"), nodeInfo.hostname());
        assertEquals(5678, (int) nodeInfo.services().get(ServiceType.BINARY));
    }

    @Test
    public void shouldNotUseExposeAlternateIfNotSet() {
        Map<String, Integer> ports = new HashMap<String, Integer>();
        ports.put("direct", 1234);
        Map<String, Integer> externalPorts = new HashMap<String, Integer>();
        externalPorts.put("kv", 5678);

        Map<AlternateAddressType, AlternateAddress> alternate = new HashMap<AlternateAddressType, AlternateAddress>();
        alternate.put(AlternateAddressType.EXTERNAL, new DefaultAlternateAddress("5.6.7.8", externalPorts));

        NodeInfo nodeInfo = new DefaultNodeInfo(null, "1.2.3.4:8091", ports, alternate);
        assertEquals("1.2.3.4", nodeInfo.rawHostname());
        assertEquals(NetworkAddress.create("1.2.3.4"), nodeInfo.hostname());
        assertEquals(1234, (int) nodeInfo.services().get(ServiceType.BINARY));
    }
