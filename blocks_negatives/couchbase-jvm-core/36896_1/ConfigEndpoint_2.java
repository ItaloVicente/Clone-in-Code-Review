    public ConfigEndpoint(String hostname, String bucket, String password, Environment environment, final RingBuffer<ResponseEvent> responseBuffer) {
        super(hostname, bucket, password, environment, responseBuffer);
    }

    @Override
    protected int port() {
        return 8091;
