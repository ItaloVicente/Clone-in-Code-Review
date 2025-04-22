    @Test
    public void shouldReadBucketUuid() {
        String raw = Resources.read("config_with_mixed_partitions.json", getClass());
        CouchbaseBucketConfig config = (CouchbaseBucketConfig)
            BucketConfigParser.parse(raw, mock(CoreEnvironment.class));

        assertEquals("aa4b515529fa706f1e5f09f21abb5c06", config.uuid());
    }

    @Test
    public void shouldHandleMissingBucketUuid() throws Exception {
        String raw = Resources.read("config_without_uuid.json", getClass());
        CouchbaseBucketConfig config = (CouchbaseBucketConfig)
                BucketConfigParser.parse(raw, mock(CoreEnvironment.class));

        assertNull(config.uuid());
    }
}
