        return Observable.from(serviceRegistry.services()).flatMap(new Func1<Service, Observable<LifecycleState>>() {
            @Override
            public Observable<LifecycleState> call(final Service service) {
                return service.connect();
            }
        }).toList().map(new Func1<List<LifecycleState>, LifecycleState>() {
            @Override
            public LifecycleState call(List<LifecycleState> state) {
                return state();
            }
        });
