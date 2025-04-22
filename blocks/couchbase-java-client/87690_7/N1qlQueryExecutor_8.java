                info.subscribe(new Action1<N1qlMetrics>() {
                    @Override
                    public void call(N1qlMetrics n1qlMetrics) {
                        Scope scope = env.tracer().scopeManager().activate(
                          response.request().span(), true
                        );
                        scope.span().setTag("peer.latency", n1qlMetrics.elapsedTime());
                        scope.close();
                    }
                });

