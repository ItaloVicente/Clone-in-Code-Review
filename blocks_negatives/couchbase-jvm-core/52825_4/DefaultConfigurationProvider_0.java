    /**
     * Represents the current cluster-wide configuration.
     */
    private final AtomicReference<ClusterConfig> currentConfig;

    /**
     * List of initial bootstrap seed hostnames.
     */
    private final AtomicReference<Set<InetAddress>> seedHosts;

