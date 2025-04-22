    private static volatile int nextStreamId = 0;
    private final String name;
    private final SerializedSubject<DCPRequest, DCPRequest> subject;
    private final String bucket;

    public DCPConnection(final CoreEnvironment env, final String name, final String bucket) {
        this.name = name;
