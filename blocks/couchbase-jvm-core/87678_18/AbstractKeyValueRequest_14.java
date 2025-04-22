
    @Override
    protected void populateSpan(Span span) {
        super.populateSpan(span);

        span.setTag("couchbase.service", "kv");
        span.setTag("couchbase.key", key);
        span.setTag("couchbase.operation_id", opaque);
    }
