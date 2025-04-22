        return Observable
            .from(endpoints)
            .flatMap(new Func1<Endpoint, Observable<LifecycleState>>() {
                @Override
                public Observable<LifecycleState> call(Endpoint endpoint) {
                    return endpoint.disconnect();
