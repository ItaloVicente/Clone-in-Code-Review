                final Scope scope = environment.tracer()
                  .buildSpan("upsert")
                  .startActive(false);
                scope.close();

              Scope encodeScope = environment.tracer()
                .buildSpan("request_encoding")
                .asChildOf(scope.span())
                .startActive(true);

