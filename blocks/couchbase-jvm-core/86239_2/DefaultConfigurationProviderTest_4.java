        String v2 = raw.replace("$REV", "2");
        provider.proposeBucketConfig(null, v2);
        assertFalse(provider.config().bucketConfigs().isEmpty());

        assertEquals(2, provider.config().bucketConfig("default").rev());
    }

    @Test
    public void shouldIgnoreConfigIfOlder() {
        DefaultConfigurationProvider provider = new DefaultConfigurationProvider(
            mock(ClusterFacade.class),
            environment
        );

        String raw = Resources.read("config_with_rev_placeholder.json", getClass());
        String v2 = raw.replace("$REV", "2");
        provider.proposeBucketConfig(null, v2);

        assertFalse(provider.config().bucketConfigs().isEmpty());
        assertEquals(2, provider.config().bucketConfig("default").rev());

        String v1 = raw.replace("$REV", "1");
        provider.proposeBucketConfig(null, v1);

        assertFalse(provider.config().bucketConfigs().isEmpty());
        assertEquals(2, provider.config().bucketConfig("default").rev());
