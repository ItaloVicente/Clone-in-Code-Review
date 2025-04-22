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
    public ActiveSpan span() {
        return span;
    }

    @Override
    public void span(final ActiveSpan span) {
        this.span = span;

        this.span.setOperationName(this.getClass().getSimpleName());
        this.span.setTag("db.type", "couchbase");
        this.span.setTag("span.kind", "client");
        this.span.setTag("component", "couchbase.sdk.java");
        if (username != null) {
            this.span.setTag("db.user", username);
        } else {
            this.span.setTag("db.user", bucket);
        }

        if (dispatchHostname != null) {
            this.span.setTag("peer.hostname", dispatchHostname);
        }

        populateSpan(this.span);

        this.continuation = this.span.capture();
        this.span.deactivate();
    }

    protected void populateSpan(final ActiveSpan span) {
    }

    @Override
    public void finishSpan() {
        this.continuation.activate().deactivate();
