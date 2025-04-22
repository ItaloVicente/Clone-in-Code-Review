
    @Test
    public void shouldIgnoreConfigIfSameRev() throws Exception {
        DefaultConfigurationProvider provider = new DefaultConfigurationProvider(
            mock(ClusterFacade.class),
            environment
        );

        TestSubscriber<ClusterConfig> subscriber = new TestSubscriber<ClusterConfig>();
        provider.configs().subscribe(subscriber);

        String raw = Resources.read("config_with_rev_placeholder.json", getClass());
        String v1 = raw.replace("$REV", "1");
        provider.proposeBucketConfig(null, v1);

        assertFalse(provider.config().bucketConfigs().isEmpty());
        assertEquals(1, provider.config().bucketConfig("default").rev());

        String v2 = raw.replace("$REV", "1");
        provider.proposeBucketConfig(null, v2);

        assertFalse(provider.config().bucketConfigs().isEmpty());
        assertEquals(1, provider.config().bucketConfig("default").rev());

        String v3 = raw.replace("$REV", "2");
        provider.proposeBucketConfig(null, v3);

        assertFalse(provider.config().bucketConfigs().isEmpty());
        assertEquals(2, provider.config().bucketConfig("default").rev());

        Thread.sleep(100);

        assertEquals(2, subscriber.getOnNextEvents().size());
    }

