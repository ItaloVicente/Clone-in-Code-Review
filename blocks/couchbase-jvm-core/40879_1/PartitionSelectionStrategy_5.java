
    private static final Endpoint selectFirstConnected(final Endpoint[] endpoints) {
        for (Endpoint endpoint : endpoints) {
            if (endpoint.isState(LifecycleState.CONNECTED)) {
                return endpoint;
            }
        }
        return null;
    }
