        return Observable.defer(new Func0<Observable<CloseBucketResponse>>() {
            @Override
            public Observable<CloseBucketResponse> call() {
                return core.send(new CloseBucketRequest(bucket));
            }
        }).map(new Func1<CloseBucketResponse, Boolean>() {
            @Override
            public Boolean call(CloseBucketResponse response) {
                return response.status().isSuccess();
            }
        });
