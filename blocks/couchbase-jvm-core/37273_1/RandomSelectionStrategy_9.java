        for (int i = 0; i < endpoints.length; i++) {
            Endpoint endpoint = endpoints[next++ % endpoints.length];
            if (endpoint.isState(LifecycleState.CONNECTED)) {
                return endpoint;
            }
        }
        return null;
