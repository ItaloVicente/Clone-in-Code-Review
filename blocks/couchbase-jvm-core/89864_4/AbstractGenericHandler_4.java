
        if (env().tracingEnabled()
            && response.request() != null
            && !response.request().isActive()
            && response.request().span() != null) {
            Scope scope = env().tracer().scopeManager()
                .activate(response.request().span(), true);
            scope.span().setBaggageItem("couchbase.zombie", "true");
            scope.close();
        }

