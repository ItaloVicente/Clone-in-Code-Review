    @Deprecated
    public <R extends CouchbaseResponse> Observable<R> send(final CouchbaseRequest request) {
        return send(new Func0<CouchbaseRequest>() {
            @Override
            public CouchbaseRequest call() {
                return request;
            }
        });
    }

    public <R extends CouchbaseResponse> Observable<R> send (final Func0<CouchbaseRequest> requestFactory) {
        return Buffers.liftForAutoRelease(Observable.defer(new Func0<Observable<R>>() {
            @Override
            public Observable<R> call() {
                return sendHot(requestFactory.call());
            }
        }));
    }

