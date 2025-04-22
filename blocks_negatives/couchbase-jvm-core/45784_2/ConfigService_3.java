        final RingBuffer<ResponseEvent> responseBuffer) {
        super(hostname, bucket, password, port, env, INITIAL_ENDPOINTS, STRATEGY, responseBuffer, FACTORY);
        pinnedEndpoints = new ArrayList<Endpoint>();
        this.hostname = hostname;
        this.bucket = bucket;
        this.password = password;
        this.port = port;
        this.env = env;
        this.responseBuffer = responseBuffer;
