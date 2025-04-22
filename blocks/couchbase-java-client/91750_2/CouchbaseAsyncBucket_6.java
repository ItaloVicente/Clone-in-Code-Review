        final Span parent;
        if (environment.tracingEnabled()) {
            Scope scope = environment.tracer()
                .buildSpan("replace_with_durability")
                .startActive(false);
            parent = scope.span();
            scope.close();
        } else {
            parent = null;
        }

        return replace(document, parent, timeout, timeUnit).flatMap(new Func1<D, Observable<D>>() {
