        return Observable
            .from(onDemandEndpoints)
            .flatMap(new Func1<Endpoint, Observable<LifecycleState>>() {
                @Override
                public Observable<LifecycleState> call(Endpoint endpoint) {
                    LOGGER.debug(logIdent(hostname, AbstractOnDemandService.this)
                        + "Initializing disconnect on Endpoint.");
                    return endpoint.disconnect();
                }
            })
            .lastOrDefault(LifecycleState.IDLE)
            .map(new Func1<LifecycleState, LifecycleState>() {
                @Override
                public LifecycleState call(final LifecycleState state) {
                    endpointStates().terminate();
                    return state();
                }
            });
    }
