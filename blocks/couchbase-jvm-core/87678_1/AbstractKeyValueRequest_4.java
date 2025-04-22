
    @Override
    protected void populateSpan(Span span) {
        super.populateSpan(span);

        span.setTag("couchbase.key", key);
        span.setTag("couchbasse.opaque", opaque);
    }
