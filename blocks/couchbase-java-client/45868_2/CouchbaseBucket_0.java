                .query(query)
                .flatMap(new Func1<AsyncQueryResult, Observable<QueryResult>>() {
                    @Override
                    public Observable<QueryResult> call(AsyncQueryResult aqr) {
                        final boolean parseSuccess = aqr.parseSuccess();
                        final String requestId = aqr.requestId();
                        final String clientContextId = aqr.clientContextId();

                        return Observable.zip(aqr.rows().toList(),
                                aqr.info().singleOrDefault(JsonObject.empty()),
                                aqr.errors().toList(),
                                aqr.finalSuccess().singleOrDefault(Boolean.FALSE),
                                new Func4<List<AsyncQueryRow>, JsonObject, List<JsonObject>, Boolean, QueryResult>() {
                                    @Override
                                    public QueryResult call(List<AsyncQueryRow> rows, JsonObject info,
                                            List<JsonObject> errors, Boolean finalSuccess) {
                                        return new DefaultQueryResult(rows, info, errors, finalSuccess, parseSuccess,
                                                requestId, clientContextId);
                                    }
                                });
                    }
                })
                .single(), timeout, timeUnit);
