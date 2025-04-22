              final ActiveSpan requestSpan = environment.tracer()
                .buildSpan("GetRequest")
                .startActive();

              ActiveSpan decodeSpan = environment.tracer()
                .buildSpan("Encode")
                .asChildOf(requestSpan)
                .startActive();

