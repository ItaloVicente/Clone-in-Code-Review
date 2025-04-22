            return insert(document, timeout, timeUnit);
        }

        final Span parent;
        if (environment.tracingEnabled()) {
            Scope scope = environment.tracer()
                .buildSpan("insert_with_durability")
                .startActive(false);
            parent = scope.span();
            scope.close();
        } else {
            parent = null;
