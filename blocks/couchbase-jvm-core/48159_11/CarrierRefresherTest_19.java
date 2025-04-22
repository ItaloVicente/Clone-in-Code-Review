        when(cluster.send(argThat(hasRequestFromFactory(GetBucketConfigRequest.class)))).thenReturn(Observable.just(
                (CouchbaseResponse) new GetBucketConfigResponse(
                        ResponseStatus.SUCCESS, ResponseStatusConverter.BINARY_SUCCESS,
                        "bucket",
                        content,
                        InetAddress.getByName("localhost")
                )
