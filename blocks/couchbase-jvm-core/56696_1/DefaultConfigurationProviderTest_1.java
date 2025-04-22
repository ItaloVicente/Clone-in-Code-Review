    @Test
    public void shouldOpenBucketIfSubsetOfNodesIsFailing() throws Exception {
        ClusterFacade cluster = mock(ClusterFacade.class);

        final Refresher refresher = mock(Refresher.class);
        when(refresher.configs()).thenReturn(Observable.<BucketConfig>empty());
        when(refresher.registerBucket(anyString(), anyString())).thenReturn(Observable.just(true));

        Loader carrierLoader = mock(Loader.class);
        Loader httpLoader = mock(Loader.class);

        final InetAddress goodNode = InetAddress.getByName("5.6.7.8");
        InetAddress badNode = InetAddress.getByName("1.2.3.4");


        when(carrierLoader.loadConfig(any(InetAddress.class), any(String.class), any(String.class)))
            .thenAnswer(new Answer<Observable<Tuple2<LoaderType, BucketConfig>>>() {
                @Override
                public Observable<Tuple2<LoaderType, BucketConfig>> answer(InvocationOnMock in) throws Throwable {
                    InetAddress target = (InetAddress) in.getArguments()[0];

                    if (target.equals(goodNode)) {
                        final BucketConfig bucketConfig = mock(BucketConfig.class);
                        when(bucketConfig.name()).thenReturn("bucket-carrier-"+target.getHostAddress());
                        return Observable.just(Tuple.create(LoaderType.Carrier, bucketConfig));
                    } else {
                        return Observable.error(new Exception("Could not load config for some reason."));
                    }
                }
            });

        when(httpLoader.loadConfig(any(InetAddress.class), any(String.class), any(String.class)))
                .thenAnswer(new Answer<Observable<Tuple2<LoaderType, BucketConfig>>>() {
                    @Override
                    public Observable<Tuple2<LoaderType, BucketConfig>> answer(InvocationOnMock in) throws Throwable {
                        InetAddress target = (InetAddress) in.getArguments()[0];

                        if (target.equals(goodNode)) {
                            final BucketConfig bucketConfig = mock(BucketConfig.class);
                            when(bucketConfig.name()).thenReturn("bucket-http-"+target.getHostAddress());
                            return Observable.just(Tuple.create(LoaderType.HTTP, bucketConfig));
                        } else {
                            return Observable.error(new Exception("Could not load config for some reason."));
                        }
                    }
                });

        ConfigurationProvider provider = new DefaultConfigurationProvider(
                cluster,
                environment,
                Arrays.asList(carrierLoader, httpLoader),
                new HashMap<LoaderType, Refresher>() {{
                    put(LoaderType.Carrier, refresher);
                    put(LoaderType.HTTP, refresher);
                }}
        );

        provider.seedHosts(Sets.newSet(badNode, goodNode), true);

        Observable<ClusterConfig> configObservable = provider.openBucket("bucket", "password");
        ClusterConfig config = configObservable.toBlocking().first();

        assertEquals(1, config.bucketConfigs().size());
        assertTrue(config.hasBucket("bucket-carrier-" + goodNode.getHostAddress()));
    }

    @Test
    public void shouldOpenBucketIfSubsetOfNodesIsFailingAndOnlyHttpAvailable() throws Exception {
        ClusterFacade cluster = mock(ClusterFacade.class);

        final Refresher refresher = mock(Refresher.class);
        when(refresher.configs()).thenReturn(Observable.<BucketConfig>empty());
        when(refresher.registerBucket(anyString(), anyString())).thenReturn(Observable.just(true));

        Loader carrierLoader = mock(Loader.class);
        Loader httpLoader = mock(Loader.class);

        final InetAddress goodNode = InetAddress.getByName("5.6.7.8");
        InetAddress badNode = InetAddress.getByName("1.2.3.4");

        when(carrierLoader.loadConfig(any(InetAddress.class), any(String.class), any(String.class)))
                .thenAnswer(new Answer<Observable<Tuple2<LoaderType, BucketConfig>>>() {
                    @Override
                    public Observable<Tuple2<LoaderType, BucketConfig>> answer(InvocationOnMock in) throws Throwable {
                        return Observable.error(new Exception("Could not load config for some reason."));
                    }
                });

        when(httpLoader.loadConfig(any(InetAddress.class), any(String.class), any(String.class)))
                .thenAnswer(new Answer<Observable<Tuple2<LoaderType, BucketConfig>>>() {
                    @Override
                    public Observable<Tuple2<LoaderType, BucketConfig>> answer(InvocationOnMock in) throws Throwable {
                        InetAddress target = (InetAddress) in.getArguments()[0];

                        if (target.equals(goodNode)) {
                            final BucketConfig bucketConfig = mock(BucketConfig.class);
                            when(bucketConfig.name()).thenReturn("bucket-http-"+target.getHostAddress());
                            return Observable.just(Tuple.create(LoaderType.HTTP, bucketConfig));
                        } else {
                            return Observable.error(new Exception("Could not load config for some reason."));
                        }
                    }
                });

        ConfigurationProvider provider = new DefaultConfigurationProvider(
                cluster,
                environment,
                Arrays.asList(carrierLoader, httpLoader),
                new HashMap<LoaderType, Refresher>() {{
                    put(LoaderType.Carrier, refresher);
                    put(LoaderType.HTTP, refresher);
                }}
        );

        provider.seedHosts(Sets.newSet(badNode, goodNode), true);

        Observable<ClusterConfig> configObservable = provider.openBucket("bucket", "password");
        ClusterConfig config = configObservable.toBlocking().first();

        assertEquals(1, config.bucketConfigs().size());
        assertTrue(config.hasBucket("bucket-http-" + goodNode.getHostAddress()));
    }

    @Test
    public void shouldOpenBucketIfSubsetOfNodesIsNotResponding() throws Exception {
        ClusterFacade cluster = mock(ClusterFacade.class);

        final Refresher refresher = mock(Refresher.class);
        when(refresher.configs()).thenReturn(Observable.<BucketConfig>empty());
        when(refresher.registerBucket(anyString(), anyString())).thenReturn(Observable.just(true));

        Loader carrierLoader = mock(Loader.class);
        Loader httpLoader = mock(Loader.class);

        final InetAddress goodNode = InetAddress.getByName("5.6.7.8");
        InetAddress badNode = InetAddress.getByName("1.2.3.4");


        when(carrierLoader.loadConfig(any(InetAddress.class), any(String.class), any(String.class)))
                .thenAnswer(new Answer<Observable<Tuple2<LoaderType, BucketConfig>>>() {
                    @Override
                    public Observable<Tuple2<LoaderType, BucketConfig>> answer(InvocationOnMock in) throws Throwable {
                        InetAddress target = (InetAddress) in.getArguments()[0];

                        if (target.equals(goodNode)) {
                            final BucketConfig bucketConfig = mock(BucketConfig.class);
                            when(bucketConfig.name()).thenReturn("bucket-carrier-"+target.getHostAddress());
                            return Observable.just(Tuple.create(LoaderType.Carrier, bucketConfig));
                        } else {
                            return Observable.timer(1, TimeUnit.MINUTES).map(new Func1<Long, Tuple2<LoaderType, BucketConfig>>() {
                                @Override
                                public Tuple2<LoaderType, BucketConfig> call(Long aLong) {
                                    throw new RuntimeException("Could not load config for some reason.");
                                }
                            });
                        }
                    }
                });

        when(httpLoader.loadConfig(any(InetAddress.class), any(String.class), any(String.class)))
                .thenAnswer(new Answer<Observable<Tuple2<LoaderType, BucketConfig>>>() {
                    @Override
                    public Observable<Tuple2<LoaderType, BucketConfig>> answer(InvocationOnMock in) throws Throwable {
                        InetAddress target = (InetAddress) in.getArguments()[0];

                        if (target.equals(goodNode)) {
                            final BucketConfig bucketConfig = mock(BucketConfig.class);
                            when(bucketConfig.name()).thenReturn("bucket-http-"+target.getHostAddress());
                            return Observable.just(Tuple.create(LoaderType.HTTP, bucketConfig));
                        } else {
                            return Observable.timer(1, TimeUnit.MINUTES).map(new Func1<Long, Tuple2<LoaderType, BucketConfig>>() {
                                @Override
                                public Tuple2<LoaderType, BucketConfig> call(Long aLong) {
                                    throw new RuntimeException("Could not load config for some reason.");
                                }
                            });
                        }
                    }
                });

        ConfigurationProvider provider = new DefaultConfigurationProvider(
                cluster,
                environment,
                Arrays.asList(carrierLoader, httpLoader),
                new HashMap<LoaderType, Refresher>() {{
                    put(LoaderType.Carrier, refresher);
                    put(LoaderType.HTTP, refresher);
                }}
        );

        provider.seedHosts(Sets.newSet(badNode, goodNode), true);

        Observable<ClusterConfig> configObservable = provider.openBucket("bucket", "password");
        ClusterConfig config = configObservable.toBlocking().first();

        assertEquals(1, config.bucketConfigs().size());
        assertTrue(config.hasBucket("bucket-carrier-" + goodNode.getHostAddress()));
    }

