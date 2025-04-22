        this.handle = null;
        this.asyncHandle = null;
    }

    public DefaultAnalyticsQueryResult(AsyncAnalyticsDeferredResultHandle asyncHandle, Object signature,
                                       AnalyticsMetrics info, List<JsonObject> errors,
                                       String finalStatus, Boolean finalSuccess, boolean parseSuccess,
                                       String requestId, String clientContextId) {

        this.asyncHandle = asyncHandle;
        this.handle = new DefaultAnalyticsDeferredResultHandle(this.asyncHandle);
        this.requestId = requestId;
        this.clientContextId = clientContextId;
        this.parseSuccess = parseSuccess;
        this.finalSuccess = finalSuccess != null && finalSuccess;
        this.status = finalStatus;
        this.signature = signature;
        this.errors = errors;
        this.info = info;
