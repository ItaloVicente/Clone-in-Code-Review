    @Override
    public Observable<Boolean> flush() {
        final String markerKey = "__flush_marker";
        return core
            .send(new UpsertRequest(markerKey, Unpooled.copiedBuffer(markerKey, CharsetUtil.UTF_8), bucket))
            .flatMap(new Func1<CouchbaseResponse, Observable<FlushResponse>>() {
                @Override
                public Observable<FlushResponse> call(CouchbaseResponse res) {
                    return core.send(new FlushRequest(bucket, password));
                }
            }).flatMap(new Func1<FlushResponse, Observable<? extends Boolean>>() {
                @Override
                public Observable<? extends Boolean> call(FlushResponse flushResponse) {
                    if (flushResponse.isDone()) {
                        return Observable.just(true);
                    }
                    while (true) {
                        GetResponse res = core.<GetResponse>send(new GetRequest(markerKey, bucket)).toBlocking().single();
                        if (res.status() == ResponseStatus.NOT_EXISTS) {
                            return Observable.just(true);
                        }
                    }
                }
            });
    }

