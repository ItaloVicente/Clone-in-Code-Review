    public SeedNodesRequest() {
        this(DEFAULT_HOSTNAME);
    }

    public SeedNodesRequest(final String... nodes) {
        this(Arrays.asList(nodes));
