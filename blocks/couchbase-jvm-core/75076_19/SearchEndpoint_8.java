        this(hostname, bucket, bucket, password, port, environment, responseBuffer);
    }

    public SearchEndpoint(String hostname, String bucket, String username, String password, int port, CoreEnvironment environment,
                          RingBuffer<ResponseEvent> responseBuffer) {
        super(hostname, bucket, username, password, port, environment, responseBuffer, false,
