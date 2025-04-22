        this(hostname, bucket, bucket, password, port, env, serviceConfig, responseBuffer, endpointFactory, selectionStrategy);
    }

    PooledService(final String hostname, final String bucket, final String username, final String password, final int port,
                  final CoreEnvironment env, final AbstractServiceConfig serviceConfig,
                  final RingBuffer<ResponseEvent> responseBuffer, final EndpointFactory endpointFactory,
                  final SelectionStrategy selectionStrategy) {
