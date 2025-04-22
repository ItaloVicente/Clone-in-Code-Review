                                final ObserveSeqnoRequest activeReq = new ObserveSeqnoRequest(token.vbucketUUID(), true, (short) 0, id, bucket);
                                final CoreEnvironment env = core.ctx().environment();
                                if (env.tracingEnabled() && parent != null) {
                                    Scope scope = env.tracer()
                                        .buildSpan("observe_seqno")
                                        .asChildOf(parent)
                                        .withTag("couchbase.active", true)
                                        .startActive(false);
                                    activeReq.span(scope.span(), env);
                                    scope.close();
                                }
                                Observable<CouchbaseResponse> masterRes = core.<CouchbaseResponse>send(activeReq).doOnUnsubscribe(new Action0() {
                                    @Override
                                    public void call() {
                                        if (env.tracingEnabled() && parent != null) {
                                            env.tracer().scopeManager()
                                                .activate(activeReq.span(), true)
                                                .close();
                                        }                                        }
                                });
