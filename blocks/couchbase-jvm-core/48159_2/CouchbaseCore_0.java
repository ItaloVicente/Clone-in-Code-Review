    public <R extends CouchbaseResponse> Observable<R> send(final CouchbaseRequest request) {
        return Buffers.wrapColdWithAutoRelease(Observable.defer(new Func0<Observable<R>>() {
            @Override
            public Observable<R> call() {
                return sendHot(request);
            }
        }));
    }

    @SuppressWarnings("unchecked")
