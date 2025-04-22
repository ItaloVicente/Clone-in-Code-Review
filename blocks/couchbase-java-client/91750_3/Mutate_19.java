                    Tracer.SpanBuilder spanBuilder = env.tracer()
                        .buildSpan("append");
                    if (parent != null) {
                        spanBuilder = spanBuilder.asChildOf(parent);
                    }
                    Scope scope = spanBuilder.startActive(false);
