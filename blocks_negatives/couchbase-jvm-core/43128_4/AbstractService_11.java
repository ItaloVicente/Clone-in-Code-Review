        Observable.combineLatest(endpointStates, new FuncN<LifecycleState>() {
            @Override
            public LifecycleState call(Object... args) {
                LifecycleState[] states = Arrays.copyOf(args, args.length, LifecycleState[].class);
                return calculateStateFrom(Arrays.asList(states));
            }
        }).subscribe(new Action1<LifecycleState>() {
            @Override
            public void call(LifecycleState state) {
                if (state == state()) {
                    return;
                }
                if (state == LifecycleState.CONNECTED) {
                    LOGGER.debug(logIdent(hostname, AbstractService.this) + "Connected Service.");
                } else if (state == LifecycleState.DISCONNECTED) {
                    LOGGER.debug(logIdent(hostname, AbstractService.this) + "Disconnected Service.");
