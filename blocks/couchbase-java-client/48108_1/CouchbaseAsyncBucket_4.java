        return Buffers.wrapColdWithAutoRelease(Observable.defer(new Func0<Observable<GetResponse>>() {
            @Override
            public Observable<GetResponse> call() {
                return core.send(new GetRequest(id, bucket, false, true, expiry));
            }
        }))
        .filter(new Func1<GetResponse, Boolean>() {
            @Override
            public Boolean call(GetResponse response) {
                if (response.status().isSuccess()) {
                    return true;
