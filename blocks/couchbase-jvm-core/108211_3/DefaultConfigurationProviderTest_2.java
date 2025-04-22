    @Test
    public void shouldSelectCarrierRefresherIfCarrierLoader() {
        Refresher carrierRefresher = mock(CarrierRefresher.class);
        when(carrierRefresher.registerBucket(anyString(), anyString(), anyString())).thenReturn(Observable.just(true));
        Refresher httpRefresher = mock(HttpRefresher.class);
        when(httpRefresher.registerBucket(anyString(), anyString(), anyString())).thenReturn(Observable.just(true));
        Map<LoaderType, Refresher> refreshers = new HashMap<>();
        refreshers.put(LoaderType.Carrier, carrierRefresher);
        refreshers.put(LoaderType.HTTP, httpRefresher);

        LoaderType loaderType = LoaderType.Carrier;

        BucketConfig config = mock(CouchbaseBucketConfig.class);
        when(config.name()).thenReturn("default");
        when(config.username()).thenReturn("user");
        when(config.password()).thenReturn("pass");

        DefaultConfigurationProvider.registerBucketForRefresh(refreshers, loaderType, config);

        verify(carrierRefresher, times(1))
            .registerBucket("default", "user", "pass");

        verify(httpRefresher, never())
            .registerBucket("default", "user", "pass");
    }

    @Test
    public void shouldSelectCarrierRefresherIfHttpLoader() {
        Refresher carrierRefresher = mock(CarrierRefresher.class);
        when(carrierRefresher.registerBucket(anyString(), anyString(), anyString())).thenReturn(Observable.just(true));
        Refresher httpRefresher = mock(HttpRefresher.class);
        when(httpRefresher.registerBucket(anyString(), anyString(), anyString())).thenReturn(Observable.just(true));
        Map<LoaderType, Refresher> refreshers = new HashMap<>();
        refreshers.put(LoaderType.Carrier, carrierRefresher);
        refreshers.put(LoaderType.HTTP, httpRefresher);

        LoaderType loaderType = LoaderType.HTTP;

        BucketConfig config = mock(CouchbaseBucketConfig.class);
        when(config.name()).thenReturn("default");
        when(config.username()).thenReturn("user");
        when(config.password()).thenReturn("pass");
        when(config.capabilities()).thenReturn(Collections.singletonList(BucketCapabilities.NODES_EXT));

        DefaultConfigurationProvider.registerBucketForRefresh(refreshers, loaderType, config);

        verify(carrierRefresher, times(1))
            .registerBucket("default", "user", "pass");

        verify(httpRefresher, never())
            .registerBucket("default", "user", "pass");
    }

    @Test
    public void shouldSelectHttpRefresherForMemcacheBucket() {
        Refresher carrierRefresher = mock(CarrierRefresher.class);
        when(carrierRefresher.registerBucket(anyString(), anyString(), anyString())).thenReturn(Observable.just(true));
        Refresher httpRefresher = mock(HttpRefresher.class);
        when(httpRefresher.registerBucket(anyString(), anyString(), anyString())).thenReturn(Observable.just(true));
        Map<LoaderType, Refresher> refreshers = new HashMap<>();
        refreshers.put(LoaderType.Carrier, carrierRefresher);
        refreshers.put(LoaderType.HTTP, httpRefresher);

        LoaderType loaderType = LoaderType.HTTP;

        BucketConfig config = mock(MemcachedBucketConfig.class);
        when(config.name()).thenReturn("default");
        when(config.username()).thenReturn("user");
        when(config.password()).thenReturn("pass");
        when(config.capabilities()).thenReturn(Collections.singletonList(BucketCapabilities.NODES_EXT));

        DefaultConfigurationProvider.registerBucketForRefresh(refreshers, loaderType, config);

        verify(carrierRefresher, never())
            .registerBucket("default", "user", "pass");

        verify(httpRefresher, times(1))
            .registerBucket("default", "user", "pass");
    }

    @Test
    public void shouldSelectHttpRefresherForOldCluster() {
        Refresher carrierRefresher = mock(CarrierRefresher.class);
        when(carrierRefresher.registerBucket(anyString(), anyString(), anyString())).thenReturn(Observable.just(true));
        Refresher httpRefresher = mock(HttpRefresher.class);
        when(httpRefresher.registerBucket(anyString(), anyString(), anyString())).thenReturn(Observable.just(true));
        Map<LoaderType, Refresher> refreshers = new HashMap<>();
        refreshers.put(LoaderType.Carrier, carrierRefresher);
        refreshers.put(LoaderType.HTTP, httpRefresher);

        LoaderType loaderType = LoaderType.HTTP;

        BucketConfig config = mock(CouchbaseBucketConfig.class);
        when(config.name()).thenReturn("default");
        when(config.username()).thenReturn("user");
        when(config.password()).thenReturn("pass");
        when(config.capabilities()).thenReturn(Collections.<BucketCapabilities>emptyList());

        DefaultConfigurationProvider.registerBucketForRefresh(refreshers, loaderType, config);

        verify(carrierRefresher, never())
            .registerBucket("default", "user", "pass");

        verify(httpRefresher, times(1))
            .registerBucket("default", "user", "pass");
    }

