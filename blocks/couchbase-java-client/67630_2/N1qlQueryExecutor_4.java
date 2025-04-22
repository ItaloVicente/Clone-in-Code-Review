    public static final Func1<? super AsyncN1qlQueryResult, ? extends Observable<? extends N1qlQueryResult>> ASYNC_RESULT_TO_SYNC = new Func1<AsyncN1qlQueryResult, Observable<N1qlQueryResult>>() {
        @Override
        public Observable<N1qlQueryResult> call(AsyncN1qlQueryResult aqr) {
            final boolean parseSuccess = aqr.parseSuccess();
            final String requestId = aqr.requestId();
            final String clientContextId = aqr.clientContextId();

            return Observable.zip(aqr.rows().toList(),
                    aqr.signature().singleOrDefault(JsonObject.empty()),
                    aqr.info().singleOrDefault(N1qlMetrics.EMPTY_METRICS),
                    aqr.errors().toList(),
                    aqr.status(),
                    aqr.finalSuccess().singleOrDefault(Boolean.FALSE),
                    new Func6<List<AsyncN1qlQueryRow>, Object, N1qlMetrics, List<JsonObject>, String, Boolean, N1qlQueryResult>() {
                        @Override
                        public N1qlQueryResult call(List<AsyncN1qlQueryRow> rows, Object signature,
                                N1qlMetrics info, List<JsonObject> errors, String finalStatus, Boolean finalSuccess) {
                            return new DefaultN1qlQueryResult(rows, signature, info, errors, finalStatus, finalSuccess,
                                    parseSuccess, requestId, clientContextId);
                        }
                    });
        }
    };

