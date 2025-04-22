
    @Override
    public Endpoint select(CouchbaseRequest request, Endpoint[] endpoints) {
        if (request instanceof GetBucketConfigRequest) {
            for (Endpoint endpoint : endpoints) {
                if (endpoint.state() == LifecycleState.CONNECTED) {
                    return endpoint;
                }
            }
        } else if (request instanceof BinaryRequest) {
            BinaryRequest binaryRequest = (BinaryRequest) request;
            short partition = binaryRequest.partition();
            int id = partition % endpoints.length;
            return endpoints[id];
        }
        return null;
    }
