        return Buffers.wrapColdWithAutoRelease(Observable.defer(new Func0<Observable<GetBucketConfigResponse>>() {
            @Override
            public Observable<GetBucketConfigResponse> call() {
                return cluster().send(new GetBucketConfigRequest(bucketName, hostname));
            }
        }))
