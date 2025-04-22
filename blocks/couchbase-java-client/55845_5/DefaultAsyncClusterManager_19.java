                })
                .map(new Func1<InsertSearchIndexResponse, IndexSettings>() {
                    @Override
                    public IndexSettings call(InsertSearchIndexResponse response) {
                        if (!response.status().isSuccess()) {
                            throw new CouchbaseException("Could not insert search index: " + response.payload());
                        }
                        return settings;
                    }
                });
    }

    @Override
    public Observable<IndexSettings> updateSearchIndex(IndexSettings settings) {
        return Observable.just(settings);
    }

    @Override
    public Observable<Boolean> hasSearchIndex(final String name) {
        return ensureServiceEnabled(ServiceType.SEARCH, environment.searchPort())
                .flatMap(new Func1<Boolean, Observable<GetSearchIndexResponse>>() {
                    @Override
                    public Observable<GetSearchIndexResponse> call(Boolean aBoolean) {
                        return core.send(new GetSearchIndexRequest(name, username, password));
                    }
                })
                .map(new Func1<GetSearchIndexResponse, Boolean>() {
                    @Override
                    public Boolean call(GetSearchIndexResponse response) {
                        return response.status().isSuccess();
                    }
                });
    }

    @Override
    public Observable<Boolean> removeSearchIndex(final String name) {
        return ensureServiceEnabled(ServiceType.SEARCH, environment.searchPort())
                .flatMap(new Func1<Boolean, Observable<RemoveSearchIndexResponse>>() {
                    @Override
                    public Observable<RemoveSearchIndexResponse> call(Boolean aBoolean) {
                        return core.send(new RemoveSearchIndexRequest(name, username, password));
                    }
                })
                .map(new Func1<RemoveSearchIndexResponse, Boolean>() {
                    @Override
                    public Boolean call(RemoveSearchIndexResponse response) {
                        return response.status().isSuccess();
                    }
                });
