    public Endpoint select(final CouchbaseRequest request, final Endpoint[] endpoints) {
        try {
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
                Endpoint endpoint = endpoints[id];
                if (endpoint != null && endpoint.state() == LifecycleState.CONNECTED) {
