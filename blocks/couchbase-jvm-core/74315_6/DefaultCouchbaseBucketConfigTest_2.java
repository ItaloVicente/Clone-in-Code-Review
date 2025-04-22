        assertFalse(config.ephemeral());
    }

    @Test
    public void shouldLoadEphemeralBucketConfig() throws Exception {
        String raw = Resources.read("ephemeral_bucket_config.json", getClass());
        CouchbaseBucketConfig config = JSON_MAPPER.readValue(raw, CouchbaseBucketConfig.class);
        assertTrue(config.ephemeral());
        assertTrue(config.serviceEnabled(ServiceType.BINARY));
        assertFalse(config.serviceEnabled(ServiceType.VIEW));
    }

    @Test
    public void shouldLoadConfigWithoutBucketCapabilities() throws Exception {
        String raw = Resources.read("config_without_capabilities.json", getClass());
        CouchbaseBucketConfig config = JSON_MAPPER.readValue(raw, CouchbaseBucketConfig.class);
        assertFalse(config.ephemeral());
        assertEquals(0, config.numberOfReplicas());
        assertEquals(64, config.numberOfPartitions());
        assertEquals(2, config.nodes().size());
        assertTrue(config.serviceEnabled(ServiceType.BINARY));
        assertTrue(config.serviceEnabled(ServiceType.VIEW));
