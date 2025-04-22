
    @Test
    public void shouldLoadConfigWithIPv6() throws Exception {
        String raw = Resources.read("config_with_ipv6.json", getClass());
        CouchbaseBucketConfig config = JSON_MAPPER.readValue(raw, CouchbaseBucketConfig.class);

        assertEquals(2, config.nodes().size());
        assertEquals("fd63:6f75:6368:2068:1471:75ff:fe25:a8be", config.nodes().get(0).hostname().address());
        assertEquals("fd63:6f75:6368:2068:c490:b5ff:fe86:9cf7", config.nodes().get(1).hostname().address());

        assertEquals(1, config.numberOfReplicas());
        assertEquals(1024, config.numberOfPartitions());
    }
