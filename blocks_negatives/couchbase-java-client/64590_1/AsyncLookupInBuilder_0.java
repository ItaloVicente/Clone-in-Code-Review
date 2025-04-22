            public Observable<MultiResult<Lookup>> call(final MultiLookupResponse multiLookupResponse) {
                return Observable.from(multiLookupResponse.responses());
            }
        })
        .map(multiCoreResultToLookupResult)
        .toList()
        .map(new Func1<List<SubdocOperationResult<Lookup>>, DocumentFragment<Lookup>>() {
            @Override
            public DocumentFragment<Lookup> call(List<SubdocOperationResult<Lookup>> lookupResults) {
                return new DocumentFragment<Lookup>(docId, 0L, null, lookupResults);
