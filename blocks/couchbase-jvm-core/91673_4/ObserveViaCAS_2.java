                                final ObserveRequest activeReq = new ObserveRequest(id, cas, true, (short) 0, bucket);
                                final CoreEnvironment env = core.ctx().environment();
                                if (env.tracingEnabled() && parent != null) {
                                    Scope scope = env.tracer()
                                        .buildSpan("observe_cas")
                                        .asChildOf(parent)
                                        .withTag("couchbase.active", true)
                                        .startActive(false);
                                    activeReq.span(scope.span(), env);
                                    scope.close();
                                }
                                Observable<ObserveResponse> masterRes = core.<ObserveResponse>send(activeReq)
                                    .doOnUnsubscribe(new Action0() {
                                        @Override
                                        public void call() {
                                            if (env.tracingEnabled() && parent != null) {
                                                env.tracer().scopeManager()
                                                    .activate(activeReq.span(), true)
                                                    .close();
                                            }                                        }
                                    });
