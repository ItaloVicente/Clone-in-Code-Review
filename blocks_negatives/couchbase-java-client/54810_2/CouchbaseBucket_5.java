                .query(query)
                .flatMap(new Func1<AsyncQueryResult, Observable<QueryResult>>() {
                    @Override
                    public Observable<QueryResult> call(AsyncQueryResult aqr) {
                        final boolean parseSuccess = aqr.parseSuccess();
                        final String requestId = aqr.requestId();
                        final String clientContextId = aqr.clientContextId();

                        return Observable.zip(aqr.rows().toList(),
                                aqr.signature().singleOrDefault(JsonObject.empty()),
                                aqr.info().singleOrDefault(QueryMetrics.EMPTY_METRICS),
                                aqr.errors().toList(),
                                aqr.finalSuccess().singleOrDefault(Boolean.FALSE),
                                new Func5<List<AsyncQueryRow>, Object, QueryMetrics,
                                        List<JsonObject>, Boolean, QueryResult>() {
                                    @Override
                                    public QueryResult call(List<AsyncQueryRow> rows, Object signature,
                                            QueryMetrics info, List<JsonObject> errors, Boolean finalSuccess) {
                                        return new DefaultQueryResult(rows, signature, info, errors, finalSuccess,
                                                parseSuccess, requestId, clientContextId);
                                    }
                                });
                    }
                })
                .single(), timeout, timeUnit);
