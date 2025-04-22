    @Test
    public void shouldForcePickServerDefault() {
        ClusterFacade cluster = mock(ClusterFacade.class);
        final Loader loader = mock(Loader.class);
        final Refresher refresher = mock(Refresher.class);
        final CoreEnvironment env = mock(CoreEnvironment.class);
        when(env.networkResolution()).thenReturn(NetworkResolution.DEFAULT);
        ConfigurationProvider provider = new DefaultConfigurationProvider(
            cluster,
            env,
            Arrays.asList(loader),
            new HashMap<LoaderType, Refresher>() {{
                put(LoaderType.Carrier, refresher);
            }}
        );

        assertTrue(provider.config().bucketConfigs().isEmpty());

        String raw = Resources.read("config_with_external.json", getClass());
        provider.proposeBucketConfig("bucket", raw);

        assertFalse(provider.config().bucketConfig("default").useAlternateNetwork());
    }

    @Test
    public void shouldForcePickExternal() {
        ClusterFacade cluster = mock(ClusterFacade.class);
        final Loader loader = mock(Loader.class);
        final Refresher refresher = mock(Refresher.class);
        final CoreEnvironment env = mock(CoreEnvironment.class);
        when(env.networkResolution()).thenReturn(NetworkResolution.EXTERNAL);
        ConfigurationProvider provider = new DefaultConfigurationProvider(
            cluster,
            env,
            Arrays.asList(loader),
            new HashMap<LoaderType, Refresher>() {{
                put(LoaderType.Carrier, refresher);
            }}
        );

        assertTrue(provider.config().bucketConfigs().isEmpty());

        String raw = Resources.read("config_with_external.json", getClass());
        provider.proposeBucketConfig("bucket", raw);

        assertTrue(provider.config().bucketConfig("default").useAlternateNetwork());
    }

    @Test
    public void shouldAutoPickExternal() {
        ClusterFacade cluster = mock(ClusterFacade.class);
        final Loader loader = mock(Loader.class);
        final Refresher refresher = mock(Refresher.class);
        final CoreEnvironment env = mock(CoreEnvironment.class);
        when(env.networkResolution()).thenReturn(NetworkResolution.AUTOMATIC);
        ConfigurationProvider provider = new DefaultConfigurationProvider(
            cluster,
            env,
            Arrays.asList(loader),
            new HashMap<LoaderType, Refresher>() {{
                put(LoaderType.Carrier, refresher);
            }}
        );

        Set<NetworkAddress> seeds = new HashSet<NetworkAddress>();
        seeds.add(NetworkAddress.create("192.168.132.234"));
        provider.seedHosts(seeds, true);

        assertTrue(provider.config().bucketConfigs().isEmpty());

        String raw = Resources.read("config_with_external.json", getClass());
        provider.proposeBucketConfig("bucket", raw);

        assertTrue(provider.config().bucketConfig("default").useAlternateNetwork());
    }

    @Test
    public void shouldAutoPickServerDefault() {
        ClusterFacade cluster = mock(ClusterFacade.class);
        final Loader loader = mock(Loader.class);
        final Refresher refresher = mock(Refresher.class);
        final CoreEnvironment env = mock(CoreEnvironment.class);
        when(env.networkResolution()).thenReturn(NetworkResolution.AUTOMATIC);
        ConfigurationProvider provider = new DefaultConfigurationProvider(
            cluster,
            env,
            Arrays.asList(loader),
            new HashMap<LoaderType, Refresher>() {{
                put(LoaderType.Carrier, refresher);
            }}
        );

        Set<NetworkAddress> seeds = new HashSet<NetworkAddress>(Arrays.asList(NetworkAddress.create("172.17.0.3")));
        provider.seedHosts(seeds, true);

        assertTrue(provider.config().bucketConfigs().isEmpty());

        String raw = Resources.read("config_with_external.json", getClass());
        provider.proposeBucketConfig("bucket", raw);

        assertFalse(provider.config().bucketConfig("default").useAlternateNetwork());
    }

