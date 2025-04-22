
    public Subject<CouchbaseResponse, CouchbaseResponse> getObservable() {
        return observable;
    }

    public ResponseEvent setObservable(final Subject<CouchbaseResponse, CouchbaseResponse> observable) {
        this.observable = observable;
        return this;
    }
