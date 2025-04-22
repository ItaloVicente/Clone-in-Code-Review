        } else if (request instanceof BinaryRequest) {
            BinaryRequest binaryRequest = (BinaryRequest) request;
            short partition = binaryRequest.partition();
            int id = partition % endpoints.length;
            Endpoint endpoint = endpoints[id];
            if (endpoint.state() == LifecycleState.CONNECTED) {
                return endpoint;
            }
