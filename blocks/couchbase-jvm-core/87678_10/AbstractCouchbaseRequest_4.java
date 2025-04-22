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
    public void span(final Span span) {
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
        if (dispatchPort != 0) {
            this.span.setTag("peer.port", dispatchPort);
        }

        populateSpan(this.span);
    }

    protected void populateSpan(final Span span) {
