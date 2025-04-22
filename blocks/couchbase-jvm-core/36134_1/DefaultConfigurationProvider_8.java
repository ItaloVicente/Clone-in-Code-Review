    private final PublishSubject<ClusterConfig> configObservable;

    private final AtomicReference<ClusterConfig> currentConfig;

    private final AtomicReference<List<String>> seedHosts;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private volatile boolean bootstrapped;

