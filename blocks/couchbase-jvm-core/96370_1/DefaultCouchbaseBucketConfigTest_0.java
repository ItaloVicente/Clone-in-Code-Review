            for (int port : addr.services().values()) {
                assertTrue(port > 0);
            }
        }
    }

    @Test
    public void shouldIncludeExternalSslIfPresent() {
        String raw = Resources.read("config_with_external_ssl.json", getClass());
        CouchbaseBucketConfig config = (CouchbaseBucketConfig)
        BucketConfigParser.parse(raw, mock(CoreEnvironment.class), NetworkAddress.localhost());

        List<NodeInfo> nodes = config.nodes();
        assertEquals(3, nodes.size());
        for (NodeInfo node : nodes) {
            Map<String, AlternateAddress> addrs = node.alternateAddresses();
            assertEquals(1, addrs.size());
            AlternateAddress addr = addrs.get(NetworkResolution.EXTERNAL.name());
            assertNotNull(addr.hostname());
            assertNotNull(addr.rawHostname());
            assertTrue(addr.services().isEmpty());
            assertFalse(addr.sslServices().isEmpty());
            for (int port : addr.sslServices().values()) {
                assertTrue(port > 0);
            }
