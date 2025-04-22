                    Tracer.SpanBuilder spanBuilder = env.tracer()
                        .buildSpan("insert");
                    if (parent != null) {
                        spanBuilder = spanBuilder.asChildOf(parent);
                    }
                    Scope scope = spanBuilder.startActive(false);
