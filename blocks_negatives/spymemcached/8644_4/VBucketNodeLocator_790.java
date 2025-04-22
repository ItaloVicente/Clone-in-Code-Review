    private final AtomicReference<TotalConfig> fullConfig;

    /**
     * Construct a VBucketNodeLocator over the given JSON configuration string.
     *
     * @param nodes
     * @param jsonConfig
     */
    public VBucketNodeLocator(List<MemcachedNode> nodes, Config jsonConfig) {
        super();
        fullConfig = new AtomicReference<TotalConfig>();
        fullConfig.set(new TotalConfig(jsonConfig, fillNodesEntries(nodes)));
