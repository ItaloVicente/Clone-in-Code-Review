
    private static final Func1<AsyncAnalyticsQueryResult, Observable<AsyncAnalyticsQueryResult>> RESULT_PEEK_FOR_RETRY =
            new Func1<AsyncAnalyticsQueryResult, Observable<AsyncAnalyticsQueryResult>>() {
                @Override
                public Observable<AsyncAnalyticsQueryResult> call(final AsyncAnalyticsQueryResult aqr) {
                    if (!aqr.parseSuccess()) {
                        final Observable<JsonObject> cachedErrors = aqr.errors().cache();

                        return cachedErrors
                                .filter(new Func1<JsonObject, Boolean>() {
                                    @Override
                                    public Boolean call(JsonObject e) {
                                        return shouldRetry(e);
                                    }
                                })
                                .lastOrDefault(null)
                                .flatMap(new Func1<JsonObject, Observable<AsyncAnalyticsQueryResult>>() {
                                    @Override
                                    public Observable<AsyncAnalyticsQueryResult> call(JsonObject errorJson) {
                                        AsyncAnalyticsQueryResult copyResult = new DefaultAsyncAnalyticsQueryResult(
                                                aqr.rows(),
                                                aqr.signature(),
                                                aqr.info(),
                                                cachedErrors,
                                                aqr.status(),
                                                aqr.parseSuccess(),
                                                aqr.requestId(),
                                                aqr.clientContextId()
                                        );
                                        if (errorJson == null) {
                                            return Observable.just(copyResult);
                                        } else {
                                            return Observable.error(
                                                new AnalyticsTemporaryFailureException(copyResult)
                                            );
                                        }
                                    }
                                });
                    } else {
                        return Observable.just(aqr);
                    }
                }
            };

    private static boolean shouldRetry(final JsonObject errorJson) {
        if (errorJson == null) return false;
        Integer code = errorJson.getInt(ERROR_FIELD_CODE);

        switch (code) {
            case 21002:
            case 23000:
            case 23003:
            case 23007:
                return true;
            default:
                return false;
        }
    }

    static class AnalyticsTemporaryFailureException extends TemporaryFailureException {
        private final AsyncAnalyticsQueryResult result;

        public AnalyticsTemporaryFailureException(AsyncAnalyticsQueryResult result) {
            this.result = result;
        }

        public AsyncAnalyticsQueryResult result() {
            return result;
        }
    }
