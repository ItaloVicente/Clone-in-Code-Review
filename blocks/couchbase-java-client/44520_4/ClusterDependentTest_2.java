    public static void ignoreIfMissing(CouchbaseFeature feature) {
        Assume.assumeTrue(clusterManager().info().checkAvailable(feature));
    }

    public static void ignoreIfClusterUnder(Version minimumVersion) {
        Assume.assumeTrue(clusterManager().info().getMinVersion().compareTo(minimumVersion) >= 0);
    }

