                                        final ObserveSeqnoRequest replReq = new ObserveSeqnoRequest(token.vbucketUUID(), false, i, id, bucket);
                                        if (env.tracingEnabled() && parent != null) {
                                            Scope scope = env.tracer()
                                                .buildSpan("observe_seqno")
                                                .asChildOf(parent)
                                                .withTag("couchbase.active", false)
                                                .startActive(false);
                                            replReq.span(scope.span(), env);
                                            scope.close();
                                        }
                                        Observable<CouchbaseResponse> res = core.send(replReq).doOnUnsubscribe(new Action0() {
                                            @Override
                                            public void call() {
                                                if (env.tracingEnabled() && parent != null) {
                                                    env.tracer().scopeManager()
                                                        .activate(replReq.span(), true)
                                                        .close();
                                                }                                        }
                                        });
