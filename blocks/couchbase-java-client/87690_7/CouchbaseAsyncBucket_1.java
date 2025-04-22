                    Tracer.SpanBuilder b = environment.tracer()
                      .buildSpan("get");
                    if (parent != null) {
                        b.asChildOf(parent);
                    }
                    final Scope scope = b.startActive(false);
                    scope.close();

