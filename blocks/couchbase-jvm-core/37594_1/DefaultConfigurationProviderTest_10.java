        Cluster cluster = mock(Cluster.class);
        Environment environment = new CouchbaseEnvironment();
        Loader errorLoader = mock(Loader.class);
        AsyncSubject<BucketConfig> errorSubject = AsyncSubject.create();
        when(errorLoader.loadConfig(any(Set.class), anyString(), anyString())).thenReturn(errorSubject);
        errorSubject.onError(new IllegalStateException());
        ConfigurationProvider provider = new DefaultConfigurationProvider(cluster, environment,
            Arrays.asList(errorLoader));
