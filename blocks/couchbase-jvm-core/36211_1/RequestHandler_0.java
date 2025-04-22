        for (final NodeInfo nodeInfo : config.nodes()) {
            addNode(nodeInfo.hostname()).flatMap(new Func1<LifecycleState, Observable<ServiceType>>() {
                @Override
                public Observable<ServiceType> call(LifecycleState lifecycleState) {
                    return Observable.from(config.services());
                }
            }).flatMap(new Func1<ServiceType, Observable<Service>>() {
                @Override
                public Observable<Service> call(ServiceType serviceType) {
                    AddServiceRequest request = new AddServiceRequest(serviceType, config.name(), nodeInfo.hostname());
                    return addService(request);
                }
            }).subscribe();
        }
