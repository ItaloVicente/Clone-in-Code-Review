        return Buffers.wrapColdWithAutoRelease(Observable.defer(new Func0<Observable<GetBucketConfigResponse>>() {
            @Override
            public Observable<GetBucketConfigResponse> call() {
                return cluster().send(new GetBucketConfigRequest(bucketName, hostname));
            }
        }))
        .doOnNext(new Action1<GetBucketConfigResponse>() {
            @Override
            public void call(GetBucketConfigResponse response) {
                if (!response.status().isSuccess()) {
                    if (response.content() != null && response.content().refCnt() > 0) {
