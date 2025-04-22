        return service.connect()
                .doOnNext(new Action1<LifecycleState>() {
                    public void call(LifecycleState state) {
                        if (state == LifecycleState.CONNECTED) {
                            signalServiceConnected(service.type());
                        }
                    }
                })
        .map(new Func1<LifecycleState, Service>() {
