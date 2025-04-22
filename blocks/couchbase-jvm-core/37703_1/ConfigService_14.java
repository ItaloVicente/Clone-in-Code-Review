    private final String hostname;
    private final String bucket;
    private final String password;
    private final int port;
    private final Environment env;
    private final RingBuffer<ResponseEvent> responseBuffer;

    private final List<Endpoint> pinnedEndpoints;

    public ConfigService(String hostname, String bucket, String password, int port, Environment env,
        final RingBuffer<ResponseEvent> responseBuffer) {
