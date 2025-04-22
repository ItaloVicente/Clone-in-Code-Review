                    Tracer.SpanBuilder spanBuilder = env.tracer()
                        .buildSpan("prepend");
                    if (parent != null) {
                        spanBuilder = spanBuilder.asChildOf(parent);
                    }
                    Scope scope = spanBuilder.startActive(false);
