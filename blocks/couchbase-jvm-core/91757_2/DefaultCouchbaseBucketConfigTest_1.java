
    @Test
    public void shouldIgnoreUnknownBucketCapabilities() {
        String raw = Resources.read("config_with_invalid_capability.json", getClass());
        BucketConfig config = BucketConfigParser.parse(raw, mock(CoreEnvironment.class));
        assertEquals(1, config.nodes().size());
    }

