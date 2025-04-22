
    @Override
    protected void populateSpan(ActiveSpan span) {
        super.populateSpan(span);

        span.setTag("couchbase.key", key);
        span.setTag("couchbase.opaque", opaque);
    }
