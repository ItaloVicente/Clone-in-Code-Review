    public DefaultAsyncAnalyticsQueryResult(AsyncAnalyticsDeferredResultHandle handle, Observable<Object> signature,
                                            Observable<AnalyticsMetrics> info, Observable<JsonObject> errors,
                                            Observable<String> finalStatus, boolean parsingSuccess, String requestId,
                                            String clientContextId) {
        this.handle = handle;
        this.signature = signature;
        this.info = info;
        this.errors = errors;
        this.finalStatus = finalStatus;
        this.parsingSuccess = parsingSuccess;
        this.requestId = requestId;
        this.clientContextId = clientContextId;
        this.rows = Observable.empty();
    }

