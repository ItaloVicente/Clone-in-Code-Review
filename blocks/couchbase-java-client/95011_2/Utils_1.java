    private static String inferOperationPrefix(final CouchbaseRequest request) {
        if (request instanceof BinaryRequest) {
            return ThresholdLogReporter.SERVICE_KV + ":";
        } else if (request instanceof QueryRequest) {
            return ThresholdLogReporter.SERVICE_N1QL + ":";
        } else if (request instanceof SearchRequest) {
            return ThresholdLogReporter.SERVICE_FTS + ":";
        } else if (request instanceof AnalyticsRequest) {
            return ThresholdLogReporter.SERVICE_ANALYTICS + ":";
        } else if (request instanceof ViewRequest) {
            return ThresholdLogReporter.SERVICE_VIEW + ":";
        } else {
            return "";
        }
    }

