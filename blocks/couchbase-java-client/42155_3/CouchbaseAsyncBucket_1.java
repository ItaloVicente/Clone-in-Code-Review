    protected final <R extends CouchbaseResponse> Observable<R> checkedSend(CouchbaseRequest request) {
        if (closed) {
            return Observable.error(new BucketClosedException("Bucket " + name() + " has been closed"));
        } else {
            return core.<R>send(request);
        }
    }

