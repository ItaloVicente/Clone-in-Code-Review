                    Tracer.SpanBuilder spanBuilder = env.tracer()
                        .buildSpan("replace");
                    if (parent != null) {
                        spanBuilder = spanBuilder.asChildOf(parent);
                    }
                    Scope scope = spanBuilder.startActive(false);
