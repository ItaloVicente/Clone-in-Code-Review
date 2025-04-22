
    @Test
    public void shouldLoadConfigWithSameNodesButDifferentPorts() throws Exception {
        String raw = Resources.read("cluster_run_two_nodes_same_host.json", getClass());
        CouchbaseBucketConfig config = JSON_MAPPER.readValue(raw, CouchbaseBucketConfig.class);
        assertFalse(config.ephemeral());
        assertEquals(1, config.numberOfReplicas());
        assertEquals(1024, config.numberOfPartitions());
        assertEquals(2, config.nodes().size());
        assertEquals("192.168.1.194", config.nodes().get(0).hostname().getHostAddress());
        assertEquals(9000, (int)config.nodes().get(0).services().get(ServiceType.CONFIG));
        assertEquals("192.168.1.194", config.nodes().get(1).hostname().getHostAddress());
        assertEquals(9001, (int)config.nodes().get(1).services().get(ServiceType.CONFIG));
    }
