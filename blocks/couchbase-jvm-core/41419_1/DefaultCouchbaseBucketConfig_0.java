    private static List<NodeInfo> buildPartitionHosts(List<NodeInfo> nodeInfos, PartitionInfo partitionInfo) {
        List<NodeInfo> partitionHosts = new ArrayList<NodeInfo>();
        for (String rawHost : partitionInfo.partitionHosts()) {
            InetAddress convertedHost = null;
            try {
                convertedHost = InetAddress.getByName(rawHost);
            } catch (UnknownHostException e) {
                throw new ConfigurationException("Could not resolve " + rawHost + "on config building.");
            }
            for (NodeInfo nodeInfo : nodeInfos) {
                if (nodeInfo.hostname().equals(convertedHost)) {
                    partitionHosts.add(nodeInfo);
                }
            }
        }
        if (partitionHosts.size() != partitionInfo.partitionHosts().size()) {
            throw new ConfigurationException("Partition size is not equal after conversion, this is a bug.");
        }
        return partitionHosts;
    }

