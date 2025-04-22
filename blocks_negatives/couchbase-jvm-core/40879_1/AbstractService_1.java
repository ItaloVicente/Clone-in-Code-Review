        return Observable.from(endpoints).flatMap(new Func1<Endpoint, Observable<LifecycleState>>() {
            @Override
            public Observable<LifecycleState> call(Endpoint endpoint) {
                return endpoint.disconnect();
            }
        }).toList().map(new Func1<List<LifecycleState>, LifecycleState>() {
            @Override
            public LifecycleState call(List<LifecycleState> endpointStates) {
                for (LifecycleState endpointState : endpointStates) {
                    if (endpointState != LifecycleState.DISCONNECTED) {
                        LOGGER.warn(AbstractService.this.getClass().getSimpleName() + " did not disconnect cleanly " +
                            "on shutdown.");
                    }
