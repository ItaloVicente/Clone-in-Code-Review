        if (span != null) {
            HashMap<String, Object> exData = new HashMap<String, Object>();
            exData.put("error.kind", "Exception");
            exData.put("error.object", throwable);
            exData.put("event", "failed");
            exData.put("message", throwable.getMessage());
            span.log(exData);
        }
    }

    @Override
    public Span span() {
        return span;
    }

    @Override
    public void span(final Span span, final CoreEnvironment env) {
        this.span = span;

        this.span.setTag("db.type", "couchbase");
        this.span.setTag("span.kind", "client");
        this.span.setTag("component", env != null ? env.userAgent() : "couchbase.sdk.java");

        populateSpan(this.span);
    }

    protected void populateSpan(final Span span) {
