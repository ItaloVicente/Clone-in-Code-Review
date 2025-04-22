
        Observable.combineLatest(endpointStates, new FuncN<LifecycleState>() {
            @Override
            public LifecycleState call(Object... args) {
                LifecycleState[] states = Arrays.copyOf(args, args.length, LifecycleState[].class);
                return calculateStateFrom(Arrays.asList(states));
            }
        }).subscribe(new Action1<LifecycleState>() {
            @Override
            public void call(LifecycleState state) {
                transitionState(state);
            }
        });
