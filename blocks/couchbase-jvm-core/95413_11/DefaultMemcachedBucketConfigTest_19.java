
    @Test
    public void shouldIncludeExternalIfPresent() {
        String raw = Resources.read("config_with_external_memcache.json", getClass());
        MemcachedBucketConfig config = (MemcachedBucketConfig) BucketConfigParser.parse(raw, ENV);

        List<NodeInfo> nodes = config.nodes();
        assertEquals(3, nodes.size());
        for (NodeInfo node : nodes) {
            Map<String, AlternateAddress> addrs = node.alternateAddresses();
            assertEquals(1, addrs.size());
            AlternateAddress addr = addrs.get(NetworkResolution.EXTERNAL.name());
            assertNotNull(addr.hostname());
            assertNotNull(addr.rawHostname());
            assertFalse(addr.services().isEmpty());
            assertTrue(addr.sslServices().isEmpty());
        }
    }
