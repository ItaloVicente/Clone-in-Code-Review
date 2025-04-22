        return service.disconnect().map(new Func1<LifecycleState, Service>() {
            @Override
            public Service call(LifecycleState lifecycleState) {
                return service;
            }
        });
