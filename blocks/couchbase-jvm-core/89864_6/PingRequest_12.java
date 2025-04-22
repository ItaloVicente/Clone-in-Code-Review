
    @Override
    protected void afterSpanSet(Span span) {
        span.setTag(Tags.PEER_SERVICE.getKey(), ThresholdLogReporter.SERVICE_VIEW);
    }

