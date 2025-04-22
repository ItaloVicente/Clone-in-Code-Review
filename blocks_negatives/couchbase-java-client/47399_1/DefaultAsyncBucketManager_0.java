        final String markerKey = "__flush_marker";
        return core
            .<UpsertResponse>send(new UpsertRequest(markerKey, Unpooled.copiedBuffer(markerKey, CharsetUtil.UTF_8), bucket))
            .flatMap(new Func1<UpsertResponse, Observable<FlushResponse>>() {
                @Override
                public Observable<FlushResponse> call(UpsertResponse res) {
                    if (res.content() != null && res.content().refCnt() > 0) {
                        res.content().release();
                    }
                    return core.send(new FlushRequest(bucket, password));
                }
            }).flatMap(new Func1<FlushResponse, Observable<? extends Boolean>>() {
                @Override
                public Observable<? extends Boolean> call(FlushResponse flushResponse) {
                    if (flushResponse.status() == ResponseStatus.FAILURE) {
                        if (flushResponse.content().contains("disabled")) {
                            return Observable.error(new FlushDisabledException("Flush is disabled for this bucket."));
                        } else {
                            return Observable.error(new CouchbaseException("Flush failed because of: "
                                + flushResponse.content()));
                        }
                    }
                    if (flushResponse.isDone()) {
                        return Observable.just(true);
                    }
                    while (true) {
                        GetResponse res = core.<GetResponse>send(new GetRequest(markerKey, bucket)).toBlocking().single();
                        if (res.content() != null && res.content().refCnt() > 0) {
                            res.content().release();
                        }
                        if (res.status() == ResponseStatus.NOT_EXISTS) {
                            return Observable.just(true);
                        }
                    }
                }
            });
