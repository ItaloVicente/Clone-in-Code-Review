
    @Test
    public void shouldIncludeExternalIfPresent() {
        String raw = Resources.read("config_with_external.json", getClass());
        CouchbaseBucketConfig config = (CouchbaseBucketConfig)
            BucketConfigParser.parse(raw, mock(CoreEnvironment.class));

        List<NodeInfo> nodes = config.nodes();
        assertEquals(3, nodes.size());
        for (NodeInfo node : nodes) {
            Map<AlternateAddressType, AlternateAddress> addrs = node.alternateAddresses();
            assertEquals(1, addrs.size());
            AlternateAddress addr = addrs.get(AlternateAddressType.EXTERNAL);
            assertNotNull(addr.hostname());
            assertNotNull(addr.rawHostname());
            assertFalse(addr.services().isEmpty());
            assertTrue(addr.sslServices().isEmpty());
        }
    }
