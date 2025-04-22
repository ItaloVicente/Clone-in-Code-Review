    public static void addRequestSpanWithParent(CouchbaseEnvironment env, Span parent, CouchbaseRequest request,
        String opName) {
        if (env.tracingEnabled()) {
            Scope scope = env.tracer()
                .buildSpan(opName)
                .asChildOf(parent)
                .startActive(false);
            request.span(scope.span(), env);
            scope.close();
        }
    }

