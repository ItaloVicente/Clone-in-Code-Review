    public static void ignoreIfMissing(CouchbaseFeature feature) {
        Assume.assumeTrue(FeaturesHelper.checkAvailable(clusterManager().info(), feature));
    }

    public static void ignoreIfClusterUnder(Version minimumVersion) {
        Assume.assumeTrue(FeaturesHelper.getMinVersion(clusterManager.info()).compareTo(minimumVersion) >= 0);
    }

