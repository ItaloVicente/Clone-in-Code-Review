
    @Override
    protected void populateSpan(Span span) {
        super.populateSpan(span);

        span.setTag("couchbase.service", "n1ql");
    }
