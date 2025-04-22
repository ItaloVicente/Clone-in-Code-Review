            Observable<Boolean> obs = addNode(nodeInfo.hostname())
                .flatMap(new Func1<LifecycleState, Observable<Map<ServiceType, Integer>>>() {
                    @Override
                    public Observable<Map<ServiceType, Integer>> call(final LifecycleState lifecycleState) {
                        Map<ServiceType, Integer> services =
                                environment.sslEnabled() ? nodeInfo.sslServices() : nodeInfo.services();
                        if (!services.containsKey(ServiceType.QUERY) && environment.queryEnabled()) {
                            services.put(ServiceType.QUERY, environment.queryPort());
                        }
                        return Observable.from(services);
