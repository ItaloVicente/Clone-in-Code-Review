
    @Test
    public void shouldLoadConfigWithMDS() throws Exception {
        String raw = Resources.read("cluster_run_three_nodes_mds_with_localhost.json", getClass());
        CouchbaseBucketConfig config = JSON_MAPPER.readValue(raw, CouchbaseBucketConfig.class);
        assertEquals(3, config.nodes().size());
        assertEquals("192.168.0.102", config.nodes().get(0).hostname().address());
        assertEquals("127.0.0.1", config.nodes().get(1).hostname().address());
        assertEquals("127.0.0.1", config.nodes().get(2).hostname().address());
        assertTrue(config.nodes().get(0).services().containsKey(ServiceType.BINARY));
        assertTrue(config.nodes().get(1).services().containsKey(ServiceType.BINARY));
        assertFalse(config.nodes().get(2).services().containsKey(ServiceType.BINARY));
    }
