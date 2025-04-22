    @Override
    public void span(final Tracer.SpanBuilder span) {
        this.span = span
            .withTag("component", "couchbase-java-sdk")
            .withTag("db.type", "couchbase")
            .withTag("db.bucket", bucket == null ? "" : bucket)
            .ignoreActiveSpan()
            .startManual();
    }

