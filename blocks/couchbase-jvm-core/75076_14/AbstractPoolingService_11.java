                                     CoreEnvironment env, int minEndpoints, int maxEndpoints, SelectionStrategy strategy,
                                     RingBuffer<ResponseEvent> responseBuffer, EndpointFactory endpointFactory) {
        this(hostname, bucket, bucket, password, port, env, minEndpoints, maxEndpoints, strategy, responseBuffer, endpointFactory);
    }

    protected AbstractPoolingService(String hostname, String bucket, String username, String password, int port,
