
    protected static void whenState(final Endpoint endpoint, final LifecycleState wanted, Action1<LifecycleState> then) {
        endpoint
                .states()
                .filter(new Func1<LifecycleState, Boolean>() {
                    @Override
                    public Boolean call(LifecycleState state) {
                        return state == wanted;
                    }
                })
                .take(1)
                .subscribe(then);
    }
