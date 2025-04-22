    @Test
    public void shouldSucceedSubsequentlyAfterFailedAttempt() {
        final CouchbaseCore core = new CouchbaseCore();

        core.send(new SeedNodesRequest(Arrays.asList(TestProperties.seedNode())));

        OpenBucketRequest badAttempt = new OpenBucketRequest(TestProperties.bucket() + "asd", TestProperties.password());
        final OpenBucketRequest goodAttempt = new OpenBucketRequest(TestProperties.bucket(), TestProperties.password());

        OpenBucketResponse response = core
            .<OpenBucketResponse>send(badAttempt)
            .onErrorResumeNext(Observable.defer(new Func0<Observable<OpenBucketResponse>>() {
                @Override
                public Observable<OpenBucketResponse> call() {
                    return core.send(goodAttempt);
                }
            }))
            .timeout(10, TimeUnit.SECONDS)
            .toBlocking()
            .single();

        assertEquals(ResponseStatus.SUCCESS, response.status());
    }

