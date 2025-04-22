    @Test
    public void shouldIgnoreFailingConfigOnManySeedNodes() throws Exception {
        ClusterFacade cluster = mock(ClusterFacade.class);
        when(cluster.send(isA(AddNodeRequest.class))).thenReturn(
                Observable.just((CouchbaseResponse) new AddNodeResponse(ResponseStatus.SUCCESS, host))
        );
        when(cluster.send(isA(AddServiceRequest.class))).thenReturn(
                Observable.just((CouchbaseResponse) new AddServiceResponse(ResponseStatus.SUCCESS, host))
        );
        Set<InetAddress> seedNodes = new HashSet<InetAddress>();
        seedNodes.add(InetAddress.getByName("10.1.1.1"));
        seedNodes.add(InetAddress.getByName("10.1.1.2"));
        seedNodes.add(InetAddress.getByName("10.1.1.3"));

        InstrumentedLoader loader = new InstrumentedLoader(2, localhostConfig, cluster, environment);
        Observable<Tuple2<LoaderType, BucketConfig>> configObservable = loader.loadConfig(seedNodes, "default", "password");

        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicInteger success = new AtomicInteger();
        final AtomicInteger failure = new AtomicInteger();
        configObservable.subscribe(new Subscriber<Tuple2<LoaderType, BucketConfig>>() {
            @Override
            public void onCompleted() {
                latch.countDown();
            }

            @Override
            public void onError(Throwable e) {
                failure.incrementAndGet();
                latch.countDown();
            }

            @Override
            public void onNext(Tuple2<LoaderType, BucketConfig> bucketConfigs) {
                success.incrementAndGet();

            }
        });
        assertTrue(latch.await(2, TimeUnit.SECONDS));
        assertEquals(1, success.get());
        assertEquals(0, failure.get());
    }

