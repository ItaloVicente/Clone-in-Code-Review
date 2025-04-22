    @Override
    public void span(Tracer.SpanBuilder span) {
        super.span(span
                .withTag("db.service", "query")
                .withTag("db.statement", query)
        );
    }

