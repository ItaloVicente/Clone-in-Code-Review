    @Deprecated
    public <R extends CouchbaseResponse> Observable<R> send(final CouchbaseRequest request) {
        return send(new RequestFactory() {
            @Override
            public CouchbaseRequest call() {
                return request;
            }
        });
    }

    @Override
    public <R extends CouchbaseResponse> Observable<R> send (final RequestFactory requestFactory) {
        return Buffers.liftForAutoRelease(Observable.defer(new Func0<Observable<R>>() {
            @Override
            public Observable<R> call() {
                return sendHot(requestFactory.call());
            }
        }));
    }

