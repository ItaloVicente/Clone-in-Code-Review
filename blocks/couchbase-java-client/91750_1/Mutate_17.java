                    Tracer.SpanBuilder spanBuilder = env.tracer()
                        .buildSpan("upsert");
                    if (parent != null) {
                        spanBuilder = spanBuilder.asChildOf(parent);
                    }
                    Scope scope = spanBuilder.startActive(false);
