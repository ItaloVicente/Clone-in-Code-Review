            return ThresholdLogReporter.SERVICE_VIEW;
        } else if (request instanceof AnalyticsRequest) {
            return ThresholdLogReporter.SERVICE_ANALYTICS;
        } else if (request instanceof SearchRequest) {
            return ThresholdLogReporter.SERVICE_FTS;
        } else if (request instanceof ConfigRequest) {
            return "config";
