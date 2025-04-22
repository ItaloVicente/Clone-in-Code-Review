    private Span startTracing(String spanName) {
        if (!environment.tracingEnabled()) {
            return null;
        }
        Scope scope = environment.tracer()
            .buildSpan(spanName)
            .startActive(false);
        Span parent = scope.span();
        scope.close();
        return parent;
    }

    private Action0 stopTracing(final Span parent) {
        return new Action0() {
            @Override
            public void call() {
                if (parent != null) {
                    environment.tracer().scopeManager()
                        .activate(parent, true)
                        .close();
                }
            }
        };
    }

