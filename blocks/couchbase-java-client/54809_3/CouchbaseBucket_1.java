                .query(query)
                .flatMap(new Func1<AsyncN1qlQueryResult, Observable<N1qlQueryResult>>() {
                    @Override
                    public Observable<N1qlQueryResult> call(AsyncN1qlQueryResult aqr) {
                        final boolean parseSuccess = aqr.parseSuccess();
                        final String requestId = aqr.requestId();
                        final String clientContextId = aqr.clientContextId();

                        return Observable.zip(aqr.rows().toList(),
                                aqr.signature().singleOrDefault(JsonObject.empty()),
                                aqr.info().singleOrDefault(N1qlMetrics.EMPTY_METRICS),
                                aqr.errors().toList(),
                                aqr.finalSuccess().singleOrDefault(Boolean.FALSE),
                                new Func5<List<AsyncN1qlQueryRow>, Object, N1qlMetrics,
                                        List<JsonObject>, Boolean, N1qlQueryResult>() {
                                    @Override
                                    public N1qlQueryResult call(List<AsyncN1qlQueryRow> rows, Object signature,
                                            N1qlMetrics info, List<JsonObject> errors, Boolean finalSuccess) {
                                        return new DefaultN1qlQueryResult(rows, signature, info, errors, finalSuccess,
                                                parseSuccess, requestId, clientContextId);
                                    }
                                });
                    }
                })
                .single(), timeout, timeUnit);
