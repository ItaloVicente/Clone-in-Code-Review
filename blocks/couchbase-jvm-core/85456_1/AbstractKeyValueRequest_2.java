    @Override
    public void span(Tracer.SpanBuilder span) {
        super.span(span
            .withTag("db.service", "kv")
            .withTag("db.key", key)
            .withTag("db.opaque", opaque)
        );
    }

