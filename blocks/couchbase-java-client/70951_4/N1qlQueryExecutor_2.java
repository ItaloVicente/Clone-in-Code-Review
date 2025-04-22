                        })
                        .lastOrDefault(null)
                        .flatMap(new Func1<JsonObject, Observable<AsyncN1qlQueryResult>>() {
                            @Override
                            public Observable<AsyncN1qlQueryResult> call(JsonObject errorJson) {
                                if (errorJson == null) {
                                    AsyncN1qlQueryResult copyResult = new DefaultAsyncN1qlQueryResult(
                                            aqr.rows(), aqr.signature(), aqr.info(),
                                            cachedErrors,
                                            aqr.status(), aqr.parseSuccess(), aqr.requestId(),
                                            aqr.clientContextId());
                                    return Observable.just(copyResult);
                                } else {
                                    return Observable.error(new QueryExecutionException("Error with prepared query",
                                            errorJson));
                                }
                            }
                        });
            } else {
                return Observable.just(aqr);
            }
