                }));
    }

    @Override
    public Observable<IndexSettings> insertSearchIndex(final IndexSettings settings) {
        return ensureServiceEnabled(ServiceType.SEARCH, environment.searchPort())
                .flatMap(new Func1<Boolean, Observable<InsertSearchIndexResponse>>() {
                    @Override
                    public Observable<InsertSearchIndexResponse> call(Boolean aBoolean) {
                        return core.send(new InsertSearchIndexRequest(settings.name(), settings.json().toString(), username, password));
