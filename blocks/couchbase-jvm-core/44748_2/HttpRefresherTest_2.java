        when(cluster.send(isA(BucketStreamingRequest.class))).thenReturn(failingResponse, successResponse);

        HttpRefresher refresher = new HttpRefresher(cluster);

        final CountDownLatch latch = new CountDownLatch(3);
        refresher.configs().subscribe(new Action1<BucketConfig>() {
            @Override
            public void call(BucketConfig bucketConfig) {
                assertEquals("default", bucketConfig.name());
                latch.countDown();
            }
        });

        Observable<Boolean> observable = refresher.registerBucket("default", "");
        assertTrue(observable.toBlocking().single());
        assertTrue(latch.await(3, TimeUnit.SECONDS));
    }

    @Test
    public void shouldFallbackToVerboseIfTerseIsNotSuccess() throws Exception {
        ClusterFacade cluster = mock(ClusterFacade.class);

        Observable<String> configStream = Observable.just(
            Resources.read("stream1.json", this.getClass()),
            Resources.read("stream2.json", this.getClass()),
            Resources.read("stream3.json", this.getClass())
        );

        Observable<CouchbaseResponse> failingResponse = Observable.just((CouchbaseResponse)
                new BucketStreamingResponse(null, "", ResponseStatus.NOT_EXISTS, null)
        );
        Observable<CouchbaseResponse> successResponse = Observable.just((CouchbaseResponse)
                new BucketStreamingResponse(configStream, "", ResponseStatus.SUCCESS, null)
        );

        when(cluster.send(isA(BucketStreamingRequest.class))).thenReturn(failingResponse, successResponse);
