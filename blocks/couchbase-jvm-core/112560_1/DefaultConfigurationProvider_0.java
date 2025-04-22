    private void updateSeedHosts() {
        ClusterConfig config = currentConfig;

        Set<String> newSeedHosts = new HashSet<>();
        for (BucketConfig bucketConfig : config.bucketConfigs().values()) {
            for (NodeInfo nodeInfo : bucketConfig.nodes()) {
                newSeedHosts.add(nodeInfo.hostname());
            }
        }

        if (!newSeedHosts.isEmpty() && !seedHosts.equals(newSeedHosts)) {
            seedHosts(newSeedHosts, true);
        }
    }

