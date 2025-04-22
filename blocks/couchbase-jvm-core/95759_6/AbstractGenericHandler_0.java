        CouchbaseRequest request = response.request();
        if (request != null && !request.isActive()) {
            if (env().operationTracingEnabled() && request.span() != null) {
                Scope scope = env().tracer().scopeManager()
                        .activate(request.span(), true);
                scope.span().setBaggageItem("couchbase.zombie", "true");
                scope.close();
            }
            if (env().zombieResponseReportingEnabled()) {
                env().zombieResponseReporter().report(response);
            }
