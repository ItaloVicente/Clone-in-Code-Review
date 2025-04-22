    @Override
    public void emit(final CouchbaseResponse response) {
        observable.onNext(response);
    }

    @Override
    public void complete() {
        observable.onCompleted();
    }

    @Override
    public void succeed(final CouchbaseResponse response) {
        emit(response);
        complete();
    }

    @Override
    public void fail(final Throwable throwable) {
        observable.onError(throwable);
    }

