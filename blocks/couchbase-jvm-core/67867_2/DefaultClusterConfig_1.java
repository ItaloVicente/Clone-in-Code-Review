
    @Override
    public Set<InetAddress> allNodeAddresses() {
        Set<InetAddress> nodes = new HashSet<InetAddress>();
        for (BucketConfig bc : bucketConfigs().values()) {
            for (NodeInfo ni : bc.nodes()) {
                nodes.add(ni.hostname());
            }
        }
        return nodes;
    }
