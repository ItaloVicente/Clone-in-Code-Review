                for (Endpoint endpoint : endpoints) {
                    if (endpoint.state() == LifecycleState.CONNECTED) {
                        return endpoint;
                    }
                }
            } else if (request instanceof BinaryRequest) {
