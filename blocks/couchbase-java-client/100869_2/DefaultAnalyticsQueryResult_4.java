        this.handle = null;
    }

    public DefaultAnalyticsQueryResult(AsyncAnalyticsHandle handle, Object signature,
                                       AnalyticsMetrics info, List<JsonObject> errors,
                                       String finalStatus, Boolean finalSuccess, boolean parseSuccess,
                                       String requestId, String clientContextId) {

        this.handle = handle;
        this.requestId = requestId;
        this.clientContextId = clientContextId;
        this.parseSuccess = parseSuccess;
        this.finalSuccess = finalSuccess != null && finalSuccess;
        this.status = finalStatus;
        this.signature = signature;
        this.errors = errors;
        this.info = info;
