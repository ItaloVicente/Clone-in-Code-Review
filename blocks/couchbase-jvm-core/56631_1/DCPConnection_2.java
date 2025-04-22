    private static volatile int nextStreamId = 0;
    private static final Map<Integer, String> streams = new ConcurrentHashMap<Integer, String>();

    private final String name;
    private final SerializedSubject<DCPRequest, DCPRequest> subject;
    private final String bucket;

    public DCPConnection(final CoreEnvironment env, final String name, final String bucket) {
        this.name = name;
