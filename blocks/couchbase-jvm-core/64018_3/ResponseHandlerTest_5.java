    @Test
    public void shouldDispatchFirstNMVImmediately() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        ClusterFacade clusterMock = mock(ClusterFacade.class);
        when(clusterMock.send(any(CouchbaseRequest.class))).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                latch.countDown();
                return null;
            }
        });

        ConfigurationProvider providerMock = mock(ConfigurationProvider.class);
        ClusterConfig clusterConfig = mock(ClusterConfig.class);
        BucketConfig bucketConfig = mock(BucketConfig.class);
        when(providerMock.config()).thenReturn(clusterConfig);
        when(clusterConfig.bucketConfig("bucket")).thenReturn(bucketConfig);
        when(bucketConfig.hasFastForwardMap()).thenReturn(true);

        ResponseHandler handler = new ResponseHandler(ENVIRONMENT, clusterMock, providerMock);

        GetRequest request = new GetRequest("key", "bucket");
        GetResponse response = new GetResponse(ResponseStatus.RETRY, (short) 0, 0L ,0, "bucket", Unpooled.EMPTY_BUFFER,
                request);

        ResponseEvent retryEvent = new ResponseEvent();
        retryEvent.setMessage(response);
        retryEvent.setObservable(request.observable());

        handler.onEvent(retryEvent, 1, true);

        long start = System.nanoTime();
        latch.await(5, TimeUnit.SECONDS);
        long end = System.nanoTime();

        assertTrue(TimeUnit.NANOSECONDS.toMillis(end - start) < 10);
    }

    @Test
    public void shouldDispatchFirstNMVBWithDelayIfNoFFMap() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        ClusterFacade clusterMock = mock(ClusterFacade.class);
        when(clusterMock.send(any(CouchbaseRequest.class))).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                latch.countDown();
                return null;
            }
        });

        ConfigurationProvider providerMock = mock(ConfigurationProvider.class);
        ClusterConfig clusterConfig = mock(ClusterConfig.class);
        BucketConfig bucketConfig = mock(BucketConfig.class);
        when(providerMock.config()).thenReturn(clusterConfig);
        when(clusterConfig.bucketConfig("bucket")).thenReturn(bucketConfig);
        when(bucketConfig.hasFastForwardMap()).thenReturn(false);

        ResponseHandler handler = new ResponseHandler(ENVIRONMENT, clusterMock, providerMock);

        GetRequest request = new GetRequest("key", "bucket");
        GetResponse response = new GetResponse(ResponseStatus.RETRY, (short) 0, 0L ,0, "bucket", Unpooled.EMPTY_BUFFER,
                request);

        ResponseEvent retryEvent = new ResponseEvent();
        retryEvent.setMessage(response);
        retryEvent.setObservable(request.observable());

        handler.onEvent(retryEvent, 1, true);

        long start = System.nanoTime();
        latch.await(5, TimeUnit.SECONDS);
        long end = System.nanoTime();

        assertTrue(TimeUnit.NANOSECONDS.toMillis(end - start) >= 100);
    }

    @Test
    public void shouldDispatchSecondNMVWithDelay() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        ClusterFacade clusterMock = mock(ClusterFacade.class);
        when(clusterMock.send(any(CouchbaseRequest.class))).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                latch.countDown();
                return null;
            }
        });

        ConfigurationProvider providerMock = mock(ConfigurationProvider.class);
        ClusterConfig clusterConfig = mock(ClusterConfig.class);
        BucketConfig bucketConfig = mock(BucketConfig.class);
        when(providerMock.config()).thenReturn(clusterConfig);
        when(clusterConfig.bucketConfig("bucket")).thenReturn(bucketConfig);
        when(bucketConfig.hasFastForwardMap()).thenReturn(true);

        ResponseHandler handler = new ResponseHandler(ENVIRONMENT, clusterMock, providerMock);

        GetRequest request = new GetRequest("key", "bucket");
        request.incrementRetryCount(); // pretend its at least once retried!
        GetResponse response = new GetResponse(ResponseStatus.RETRY, (short) 0, 0L ,0, "bucket", Unpooled.EMPTY_BUFFER,
            request);

        ResponseEvent retryEvent = new ResponseEvent();
        retryEvent.setMessage(response);
        retryEvent.setObservable(request.observable());

        handler.onEvent(retryEvent, 1, true);

        long start = System.nanoTime();
        latch.await(5, TimeUnit.SECONDS);
        long end = System.nanoTime();

        assertTrue(TimeUnit.NANOSECONDS.toMillis(end - start) >= 100);
    }

