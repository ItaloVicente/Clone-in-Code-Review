    /**
     * Waits until the endpoint goes into the given state, calls the action and then unsubscribes.
     *
     * @param endpoint the endpoint to monitor.
     * @param wanted the wanted state.
     * @param then the action to execute when the state is reached.
     */
    private void whenState(final Endpoint endpoint, final LifecycleState wanted, Action1<LifecycleState> then) {
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
