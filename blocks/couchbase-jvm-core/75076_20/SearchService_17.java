        this(hostname, bucket, bucket, password, port, env, responseBuffer);
    }

    public SearchService(final String hostname, final String bucket, final String username, final String password, final int port,
                         final CoreEnvironment env, final RingBuffer<ResponseEvent> responseBuffer) {
        super(hostname, bucket, username, password, port, env, env.searchServiceConfig(), responseBuffer, FACTORY, STRATEGY);
