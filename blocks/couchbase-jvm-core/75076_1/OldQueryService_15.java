
    public OldQueryService(final String hostname, final String bucket, final String password,
                           final int port, final CoreEnvironment env, final RingBuffer<ResponseEvent> responseBuffer) {
        this(hostname, bucket, bucket, password, port, env, responseBuffer);
    }

