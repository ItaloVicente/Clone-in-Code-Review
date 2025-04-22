                final Scope scope = environment.tracer()
                  .buildSpan("UpsertRequest")
                  .startActive(false);
                scope.close();

              Scope encodeScope = environment.tracer()
                .buildSpan("Encode")
                .asChildOf(scope.span())
                .startActive(true);

