                                     CoreEnvironment env, int minEndpoints, int maxEndpoints, SelectionStrategy strategy,
                                     RingBuffer<ResponseEvent> responseBuffer, EndpointFactory endpointFactory) {
        super(hostname, bucket, bucket, password, port, env, minEndpoints, responseBuffer, endpointFactory);
        this.maxEndpoints = maxEndpoints;
        this.responseBuffer = responseBuffer;
        this.strategy = strategy;
        this.env = env;
    }

    protected AbstractPoolingService(String hostname, String bucket, String username, String password, int port,
