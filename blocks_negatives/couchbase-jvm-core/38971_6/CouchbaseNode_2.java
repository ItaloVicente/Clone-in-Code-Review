        this.serviceStates = Collections.synchronizedList(new ArrayList<Observable<LifecycleState>>());

        stateSubscription = Observable.combineLatest(serviceStates, new FuncN<LifecycleState>() {
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

                LifecycleState before = state();
                transitionState(state);

                if (state() == LifecycleState.CONNECTED) {
                    LOGGER.info("Connected to Node " + hostname);
                } else if ((before == LifecycleState.CONNECTED || before == LifecycleState.DISCONNECTING)
                    && state() == LifecycleState.DISCONNECTED) {
                    LOGGER.info("Disconnected from Node " + hostname);
                }


            }
        });

