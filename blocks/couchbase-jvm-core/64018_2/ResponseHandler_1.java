
    private static boolean bucketHasFastForwardMap(String bucketName, ClusterConfig clusterConfig) {
        if (bucketName == null) {
            return false;
        }
        BucketConfig bucketConfig = clusterConfig.bucketConfig(bucketName);
        return bucketConfig != null && bucketConfig.hasFastForwardMap();
    }
