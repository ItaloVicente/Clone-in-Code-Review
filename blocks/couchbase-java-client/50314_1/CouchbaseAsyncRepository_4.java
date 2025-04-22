            })
            .map(new DocumentToType<T>(documentClass));
    }

    @Override
    public <T> Observable<T> getAndLock(String id, final int lockTime, Class<T> documentClass) {
        return Observable
            .just(id)
            .flatMap(new Func1<String, Observable<JsonDocument>>() {
                @Override
                public Observable<JsonDocument> call(String id) {
                    return bucket.getAndLock(id, lockTime);
                }
            })
            .map(new DocumentToType<T>(documentClass));
    }

    @Override
    public <T> Observable<T> getAndTouch(String id, final int expiry, Class<T> documentClass) {
        return Observable
            .just(id)
            .flatMap(new Func1<String, Observable<JsonDocument>>() {
                @Override
                public Observable<JsonDocument> call(String id) {
                    return bucket.getAndTouch(id, expiry);
                }
            })
            .map(new DocumentToType<T>(documentClass));
