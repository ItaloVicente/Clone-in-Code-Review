              final Span requestSpan = environment.tracer()
                .buildSpan("GetRequest")
                .startActive(true).span();

              Span decodeSpan = environment.tracer()
                .buildSpan("Encode")
                .asChildOf(requestSpan)
                .startActive(true).span();

