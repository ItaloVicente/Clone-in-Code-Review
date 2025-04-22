    private void reconfigure() {
        ClusterConfig config = configuration.get();

        Set<InetAddress> configNodes = new HashSet<InetAddress>();
        for (Map.Entry<String, BucketConfig> bucket : config.bucketConfigs().entrySet()) {
            BucketConfig bucketConfig = bucket.getValue();
            for (final NodeInfo node : bucketConfig.nodes()) {
                configNodes.add(node.hostname());
            }
            reconfigureBucket(bucketConfig);
        }
