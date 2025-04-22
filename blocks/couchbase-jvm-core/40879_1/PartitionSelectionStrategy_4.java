                if (partition > 0) {
                    int id = partition % numEndpoints;
                    Endpoint endpoint = endpoints[id];
                    if (endpoint != null && endpoint.isState(LifecycleState.CONNECTED)) {
                        return endpoint;
                    }
                } else {
                    return selectFirstConnected(endpoints);
