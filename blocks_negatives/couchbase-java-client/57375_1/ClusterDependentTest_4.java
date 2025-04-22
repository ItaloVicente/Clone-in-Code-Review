        cluster.disconnect();
    }

    /**
     * By calling this in @BeforeClass with a {@link CouchbaseFeature},
     * tests will be skipped is said feature is not available on the cluster.
     *
     * @param feature the feature to check for.
     */
    public static void ignoreIfMissing(CouchbaseFeature feature) {
        Assume.assumeTrue(clusterManager().info().checkAvailable(feature));
    }

    /**
     * By calling this in @BeforeClass with a {@link Version},
     * tests will be skipped is all nodes in the cluster are not above
     * or at that version.
     *
     * @param minimumVersion the required version to check for.
     */
    public static void ignoreIfClusterUnder(Version minimumVersion) {
        Assume.assumeTrue(clusterManager().info().getMinVersion().compareTo(minimumVersion) >= 0);
