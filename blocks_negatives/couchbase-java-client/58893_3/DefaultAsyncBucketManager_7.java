                    Statement buildStatement = Index.buildIndex().on(bucket)
                        .indexes(pendingIndexes)
                        .using(IndexType.GSI);

                    return queryExecutor.execute(N1qlQuery.simple(buildStatement))
                        .flatMap(new Func1<AsyncN1qlQueryResult, Observable<List<String>>>() {
                            @Override
                            public Observable<List<String>> call(final AsyncN1qlQueryResult aqr) {
                                return aqr.finalSuccess()
                                    .flatMap(new Func1<Boolean, Observable<List<String>>>() {
                                        @Override
                                        public Observable<List<String>> call(
                                            Boolean success) {
                                            if (success) {
                                                return Observable.just(pendingIndexes);
                                            } else {
                                                return aqr.errors().toList().flatMap(errorHandler);
                                            }
                                        }
                                    });
                            }
                        });
                }
            });
    }

    @Override
    public Observable<IndexInfo> watchIndex(final String indexName, long watchTimeout, TimeUnit watchTimeUnit) {
        return listIndexes()
            .flatMap(new Func1<IndexInfo, Observable<IndexInfo>>() {
                @Override
                public Observable<IndexInfo> call(IndexInfo i) {
                    if (!indexName.equals(i.name())) {
                        return Observable.empty();
                    } else if (!"online".equals(i.state()))
                        return Observable.error(new IndexesNotReadyException("Index not ready: " + i.name()));
                    else {
                        return Observable.just(i);
