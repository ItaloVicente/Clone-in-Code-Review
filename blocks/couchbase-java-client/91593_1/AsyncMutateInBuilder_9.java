            public Observable<DocumentFragment<Mutation>> call() {
                Span requestSpan = null;
                if (environment.tracingEnabled()) {
                    Scope scope = environment.tracer()
                        .buildSpan("subdoc_mutate")
                        .startActive(false);
                    requestSpan = scope.span();
                    scope.close();
                }


                Scope encodeScope = null;
                if (requestSpan != null) {
                    encodeScope = environment.tracer()
                        .buildSpan("request_encoding")
                        .asChildOf(requestSpan)
                        .startActive(true);
                }

