            .map(new DocumentToType<T>(documentClass));
    }

    @Override
    public <T> Observable<T> getFromReplica(String id, final ReplicaMode type, Class<T> documentClass) {
        return Observable
            .just(id)
            .flatMap(new Func1<String, Observable<JsonDocument>>() {
