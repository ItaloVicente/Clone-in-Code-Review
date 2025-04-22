    private static Endpoint selectByPartition(final Endpoint[] endpoints, final short partition) {
        if (partition >= 0) {
            int numEndpoints = endpoints.length;
            Endpoint endpoint = numEndpoints == 1 ? endpoints[0] : endpoints[partition % numEndpoints];
            if (endpoint != null && endpoint.isState(LifecycleState.CONNECTED)) {
                return endpoint;
            }
            return null;
        } else {
            return selectFirstConnected(endpoints);
        }
