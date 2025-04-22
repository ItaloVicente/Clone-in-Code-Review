        try {
            for (Endpoint p : endpoints) {
                Endpoint endpoint = endpoints[next++ % endpoints.length];
                if (endpoint.isState(LifecycleState.CONNECTED)) {
                    return endpoint;
                }
            }
        } catch(Exception ex) {
