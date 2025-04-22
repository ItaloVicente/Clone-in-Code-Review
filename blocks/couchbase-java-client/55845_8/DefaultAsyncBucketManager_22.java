    @Override
    public Observable<IndexSettings> updateSearchIndex(IndexSettings settings) {
        return Observable.just(settings);
    }

    @Override
    public Observable<Boolean> hasSearchIndex(final String name) {
        return Observable.defer(new Func0<Observable<GetSearchIndexResponse>>() {
