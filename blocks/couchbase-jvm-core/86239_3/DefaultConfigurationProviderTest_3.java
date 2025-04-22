    public void shouldAcceptProposedConfigIfNoneExists() {
        DefaultConfigurationProvider provider = new DefaultConfigurationProvider(
            mock(ClusterFacade.class),
            environment
        );

        assertTrue(provider.config().bucketConfigs().isEmpty());

        String raw = Resources.read("config_with_rev_placeholder.json", getClass());
        raw = raw.replace("$REV", "1");
        provider.proposeBucketConfig(null, raw);

        assertFalse(provider.config().bucketConfigs().isEmpty());
        assertEquals(1, provider.config().bucketConfig("default").rev());
    }

    @Test
    public void shouldAcceptProposedConfigIfNewer() {
        DefaultConfigurationProvider provider = new DefaultConfigurationProvider(
            mock(ClusterFacade.class),
            environment
        );

        String raw = Resources.read("config_with_rev_placeholder.json", getClass());
        String v1 = raw.replace("$REV", "1");
        provider.proposeBucketConfig(null, v1);

        assertFalse(provider.config().bucketConfigs().isEmpty());
        assertEquals(1, provider.config().bucketConfig("default").rev());

        String v2 = raw.replace("$REV", "2");
        provider.proposeBucketConfig(null, v2);

        assertFalse(provider.config().bucketConfigs().isEmpty());
        assertEquals(2, provider.config().bucketConfig("default").rev());
    }

    @Test
    public void shouldIgnoreConfigIfInvalid() {
        DefaultConfigurationProvider provider = new DefaultConfigurationProvider(
            mock(ClusterFacade.class),
            environment
        );

        assertTrue(provider.config().bucketConfigs().isEmpty());


        String raw = Resources.read("config_with_rev_placeholder.json", getClass());
        provider.proposeBucketConfig(null, raw);
        assertTrue(provider.config().bucketConfigs().isEmpty());


        String v1 = raw.replace("$REV", "1");
        provider.proposeBucketConfig(null, v1);
        assertFalse(provider.config().bucketConfigs().isEmpty());

        provider.proposeBucketConfig(null, raw);
        assertFalse(provider.config().bucketConfigs().isEmpty());
