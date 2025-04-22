        this(hostname, bucket, bucket, password, port, env, responseBuffer);
    }

    public DCPService(String hostname, String bucket, String username, String password, int port, CoreEnvironment env,
                      RingBuffer<ResponseEvent> responseBuffer) {
        super(hostname, bucket, username, password, port, env, responseBuffer, FACTORY);
