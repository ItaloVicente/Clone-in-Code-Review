    @Override
    public Span span() {
        return span;
    }

    @Override
    public void span(Span span, CoreEnvironment env) {
        this.span = span;

        this.span.setTag(Tags.DB_TYPE.getKey(), "couchbase");
        this.span.setTag(Tags.SPAN_KIND.getKey(), "client");
        this.span.setTag(Tags.COMPONENT.getKey(), env != null ? env.userAgent() : "couchbase.sdk.java");

        afterSpanSet(this.span);
    }

    protected void afterSpanSet(final Span span) { }

