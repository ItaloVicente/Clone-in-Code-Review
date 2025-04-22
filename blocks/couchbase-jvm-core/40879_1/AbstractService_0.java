        return Observable
            .from(endpoints)
            .flatMap(new Func1<Endpoint, Observable<LifecycleState>>() {
                @Override
                public Observable<LifecycleState> call(final Endpoint endpoint) {
                    return endpoint.connect();
                }
            })
            .last()
            .map(new Func1<LifecycleState, LifecycleState>() {
                @Override
                public LifecycleState call(final LifecycleState state) {
                    return state();
                }
            });
