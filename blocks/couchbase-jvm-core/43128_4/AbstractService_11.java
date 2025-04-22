        Observable
            .combineLatest(endpointStates, new FuncN<LifecycleState>() {
                @Override
                public LifecycleState call(Object... args) {
                    LifecycleState[] states = Arrays.copyOf(args, args.length, LifecycleState[].class);
                    return calculateStateFrom(Arrays.asList(states));
