    private static final CouchbaseLogger LOGGER = CouchbaseLoggerFactory.getInstance(Service.class);

    private final List<Endpoint> onDemandEndpoints = new CopyOnWriteArrayList<Endpoint>();

    private volatile boolean disconnect = false;

    private final CoreContext ctx;

    private final String hostname;

    protected AbstractOnDemandService(String hostname, String bucket, String username, String password, int port,
        CoreContext ctx, EndpointFactory endpointFactory) {
