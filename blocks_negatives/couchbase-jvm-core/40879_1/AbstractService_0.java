        return Observable.from(endpoints).flatMap(new Func1<Endpoint, Observable<LifecycleState>>() {
            @Override
            public Observable<LifecycleState> call(final Endpoint endpoint) {
                return endpoint.connect();
            }
        }).toList().map(new Func1<List<LifecycleState>, LifecycleState>() {
            @Override
            public LifecycleState call(List<LifecycleState> endpointStates) {
                return state();
            }
        });
