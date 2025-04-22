                                        final ObserveRequest replReq  = new ObserveRequest(id, cas, false, i, bucket);
                                        if (env.tracingEnabled() && parent != null) {
                                            Scope scope = env.tracer()
                                                .buildSpan("observe_cas")
                                                .asChildOf(parent)
                                                .withTag("couchbase.active", false)
                                                .startActive(false);
                                            replReq.span(scope.span(), env);
                                            scope.close();
                                        }
                                        Observable<ObserveResponse> res = core.<ObserveResponse>send(replReq)
                                            .doOnUnsubscribe(new Action0() {
                                                @Override
                                                public void call() {
                                                    if (env.tracingEnabled() && parent != null) {
                                                        env.tracer().scopeManager()
                                                            .activate(replReq.span(), true)
                                                            .close();
                                                    }
                                                }
                                            });
