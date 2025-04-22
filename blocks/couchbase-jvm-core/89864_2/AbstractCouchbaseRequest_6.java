        if (span != null) {
            Map<String, Object> exData = new HashMap<String, Object>();
            exData.put(Fields.ERROR_KIND, "Exception");
            exData.put(Fields.ERROR_OBJECT, throwable);
            exData.put(Fields.EVENT, "failed");
            exData.put(Fields.MESSAGE, throwable.getMessage());
            span.log(exData);
        }
    }

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

        if (operationId() != null) {
            this.span.setTag("couchbase.operation_id", operationId());
        }

        afterSpanSet(this.span);
    }

    protected void afterSpanSet(final Span span) { }

    @Override
    public String operationId() {
        return null;
