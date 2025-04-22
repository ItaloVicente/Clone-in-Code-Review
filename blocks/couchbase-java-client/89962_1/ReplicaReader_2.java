
                Observable<D> result = assembleRequests(core, id, type, bucket)
                  .flatMap(new Func1<BinaryRequest, Observable<D>>() {
                      @Override
                      public Observable<D> call(BinaryRequest request) {
                          String name = request instanceof ReplicaGetRequest ? "get_replica" : "get";
                          if (environment.tracingEnabled()) {
                              Scope scope = environment.tracer()
                                .buildSpan(name)
                                .asChildOf(parentSpan)
                                .startActive(false);
                              request.span(scope.span(), environment);
                              scope.close();
                          }

                          Observable<GetResponse> result = core
                            .<GetResponse>send(request)
                            .filter(new Get.GetFilter(environment));

                          if (timeout > 0) {
                              result = result.timeout(timeout, timeUnit, environment.scheduler());
                          }

                          return result.onErrorResumeNext(GetResponseErrorHandler.INSTANCE)
                            .map(new Get.GetMap(environment, transcoders, target, id));
                      }
                  });

                if (timeout > 0) {
                    result = result.timeout(timeout, timeUnit, environment.scheduler());
                }

                return result.doOnTerminate(new Action0() {
                      @Override
                      public void call() {
                          if (environment.tracingEnabled() && parentSpan != null) {
                              environment.tracer().scopeManager()
                                .activate(parentSpan, true)
                                .close();
                          }
                      }
                  })
                  .cacheWithInitialCapacity(type.maxAffectedNodes());
            }
        });
