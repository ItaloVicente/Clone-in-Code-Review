                Tracer.SpanBuilder b = env.tracer()
                  .buildSpan("n1ql")
                  .withTag("db.statement", query.statement().toString())
                  .withTag("couchbase.operation_id", query.params().contextId());
                if (parent != null) {
                    b.asChildOf(parent);
                }
                final Scope scope = b.startActive(false);
                scope.close();

                GenericQueryRequest n1qlRequest = createN1qlRequest(query, bucket, username, password, null);
                n1qlRequest.span(scope.span(), env);
                return core.send(n1qlRequest);
