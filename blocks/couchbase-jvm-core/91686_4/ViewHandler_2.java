        CouchbaseRequest request = currentRequest();
        if (request != null && request.span() != null) {
            if (env().tracingEnabled()) {
                env().tracer().scopeManager()
                    .activate(request.span(), true)
                    .close();
            }
        }
