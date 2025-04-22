
        for (int i = 0; i < MAX_TRIES; i++) {
            int rand = RANDOM.nextInt(endpoints.length);
            Endpoint endpoint = endpoints[rand];
            if (endpoint.isState(LifecycleState.CONNECTED)) {
                return endpoint;
            }
        }

