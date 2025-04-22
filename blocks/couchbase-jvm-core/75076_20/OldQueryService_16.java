    @Deprecated
    public OldQueryService(final String hostname, final String bucket, final String password,
                           final int port, final CoreEnvironment env, final RingBuffer<ResponseEvent> responseBuffer) {
        this(hostname, bucket, bucket, password, port, env, responseBuffer);
    }

    public OldQueryService(final String hostname, final String bucket, final String username, final String password,
                           final int port, final CoreEnvironment env, final RingBuffer<ResponseEvent> responseBuffer) {
        super(hostname, bucket, username, password, port, env, env.queryEndpoints(), env.queryEndpoints(), STRATEGY,
