    @Test
    public void shouldReadBucketUuid() throws Exception {
        String raw = Resources.read("memcached_mixed_sherlock.json", getClass());
        MemcachedBucketConfig config = (MemcachedBucketConfig) BucketConfigParser.parse(raw, ENV);

        assertEquals("7b6c811c94f985b685d99596816a7a9f", config.uuid());
    }
