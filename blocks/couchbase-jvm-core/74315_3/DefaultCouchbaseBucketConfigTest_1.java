        assertFalse(config.ephemeral());
    }

    @Test
    public void shouldLoadEphemeralBucketConfig() throws Exception {
        String raw = Resources.read("ephemeral_bucket_config.json", getClass());
        CouchbaseBucketConfig config = JSON_MAPPER.readValue(raw, CouchbaseBucketConfig.class);
        assertTrue(config.ephemeral());
