
    @Override
    public Observable<Boolean> removeSearchIndex(final String name) {
        return Observable.defer(new Func0<Observable<RemoveSearchIndexResponse>>() {
            @Override
            public Observable<RemoveSearchIndexResponse> call() {
                return core.send(new RemoveSearchIndexRequest(name, bucket, password));
            }
        }).map(new Func1<RemoveSearchIndexResponse, Boolean>() {
            @Override
            public Boolean call(RemoveSearchIndexResponse response) {
                return response.status().isSuccess();
            }
        });
    }

