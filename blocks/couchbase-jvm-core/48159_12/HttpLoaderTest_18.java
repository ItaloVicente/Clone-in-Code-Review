
        when(cluster.send(argThat(hasRequestFromFactory(BucketConfigRequest.class)))).thenAnswer(
                new Answer<Observable<CouchbaseResponse>>() {
            int count = 0;

            List<Observable<CouchbaseResponse>> responses = Arrays.asList(
                Observable.just(
                    (CouchbaseResponse) new BucketConfigResponse(null, ResponseStatus.FAILURE)
                ),
                Observable.just(
                    (CouchbaseResponse) new BucketConfigResponse("verboseConfig", ResponseStatus.SUCCESS)
                )
            );

            @Override
            public Observable<CouchbaseResponse> answer(InvocationOnMock invocation) throws Throwable {
                return responses.get(count++);
            }
        });
