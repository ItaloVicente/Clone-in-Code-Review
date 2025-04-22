            public Observable<DocumentFragment<Lookup>> call(final MultiLookupResponse mlr) {
                return Observable
                    .from(mlr.responses()).map(multiCoreResultToLookupResult)
                    .toList()
                    .map(new Func1<List<SubdocOperationResult<Lookup>>, DocumentFragment<Lookup>>() {
                        @Override
                        public DocumentFragment<Lookup> call(List<SubdocOperationResult<Lookup>> lookupResults) {
                            return new DocumentFragment<Lookup>(docId, mlr.cas(), null, lookupResults);
                        }
                    });
