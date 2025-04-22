                GenericQueryRequest n1qlRequest = createN1qlRequest(query, bucket, username, password, null);
                if (env != null) {
                    Tracer.SpanBuilder builder = env.tracer().buildSpan("N1qlRequest");
                    if (parent != null) {
                        builder.asChildOf(parent);
                    }
                    n1qlRequest.span(builder);
                }
                return core.send(n1qlRequest);
