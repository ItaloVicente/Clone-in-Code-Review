        Endpoint endpoint;
        do {
            int rand = new Random().nextInt(endpoints.length);
            endpoint = endpoints[rand];
        } while(endpoint.state() != LifecycleState.CONNECTED);
        return endpoint;
