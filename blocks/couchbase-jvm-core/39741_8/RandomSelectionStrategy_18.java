    public Endpoint select(final CouchbaseRequest request, final Endpoint[] endpoints) {
        try {
            for (Endpoint p : endpoints) {
                Endpoint endpoint = endpoints[next++ % endpoints.length];
                if (endpoint.isState(LifecycleState.CONNECTED)) {
                    return endpoint;
                }
