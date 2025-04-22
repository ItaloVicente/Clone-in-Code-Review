        Observable<CouchbaseResponse> terseResponse = Observable.just(
                (CouchbaseResponse) new BucketConfigResponse(null, ResponseStatus.FAILURE)
        );
        Observable<CouchbaseResponse> verboseResponse = Observable.just(
                (CouchbaseResponse) new BucketConfigResponse("verboseConfig", ResponseStatus.SUCCESS)
        );
        when(cluster.send(isA(BucketConfigRequest.class))).thenReturn(terseResponse);
        when(cluster.send(isA(BucketConfigRequest.class))).thenReturn(verboseResponse);
