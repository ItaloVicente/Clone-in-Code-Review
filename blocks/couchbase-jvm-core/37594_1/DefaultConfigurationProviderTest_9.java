        Loader successLoader = mock(Loader.class);
        Loader errorLoader = mock(Loader.class);
        when(successLoader.loadConfig(any(Set.class), anyString(), anyString()))
            .thenReturn(Observable.from(mock(BucketConfig.class)));
        AsyncSubject<BucketConfig> errorSubject = AsyncSubject.create();
        when(errorLoader.loadConfig(any(Set.class), anyString(), anyString())).thenReturn((Observable) errorSubject);
        errorSubject.onError(new IllegalStateException());

        ConfigurationProvider provider = new DefaultConfigurationProvider(cluster, environment,
            Arrays.asList(errorLoader, successLoader));

        Observable<ClusterConfig> configObservable = provider.openBucket("bucket", "password");
        ClusterConfig config = configObservable.toBlockingObservable().first();
        assertTrue(config.hasBucket("bucket"));
        assertFalse(config.hasBucket("other"));
    }

    @Test
    public void shouldEmitNewClusterConfig() throws Exception {
        final Cluster cluster = mock(Cluster.class);
        Environment environment = new CouchbaseEnvironment();
        Loader loader = mock(Loader.class);
        when(loader.loadConfig(any(Set.class), anyString(), anyString()))
            .thenReturn(Observable.from(mock(BucketConfig.class)));

        ConfigurationProvider provider = new DefaultConfigurationProvider(cluster, environment, Arrays.asList(loader));
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicReference<ClusterConfig> configReference = new AtomicReference<ClusterConfig>();
        provider.configs().subscribe(new Action1<ClusterConfig>() {
            @Override
            public void call(ClusterConfig clusterConfig) {
                configReference.set(clusterConfig);
                latch.countDown();
            }
        });

        Observable<ClusterConfig> configObservable = provider.openBucket("bucket", "password");
        ClusterConfig config = configObservable.toBlockingObservable().first();
        assertTrue(config.hasBucket("bucket"));
        assertFalse(config.hasBucket("other"));
        assertTrue(latch.await(2, TimeUnit.SECONDS));
        assertEquals(configReference.get(), config);
