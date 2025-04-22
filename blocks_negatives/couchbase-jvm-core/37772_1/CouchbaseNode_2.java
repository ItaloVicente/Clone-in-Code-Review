        return Observable.from(serviceRegistry.services()).flatMap(new Func1<Service, Observable<LifecycleState>>() {
            @Override
            public Observable<LifecycleState> call(final Service service) {
                return service.disconnect();
            }
        }).toList().map(new Func1<List<LifecycleState>, LifecycleState>() {
            @Override
            public LifecycleState call(final List<LifecycleState> serviceStates) {
                LifecycleState nodeState = calculateStateFrom(serviceStates);
                transitionState(nodeState);
                return nodeState;
            }
        });
