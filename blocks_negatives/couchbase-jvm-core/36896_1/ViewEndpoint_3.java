    public ViewEndpoint(final String hostname, String bucket, String password, final Environment env, final RingBuffer<ResponseEvent> responseBuffer) {
        super(hostname, bucket, password, env, responseBuffer);
    }

    @Override
    protected int port() {
        return PORT;
