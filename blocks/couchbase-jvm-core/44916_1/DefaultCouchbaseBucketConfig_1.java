    private static Set<InetAddress> buildNodesWithPrimaryPartitions(final List<NodeInfo> nodeInfos,
        final List<Partition> partitions) {
        Set<InetAddress> nodes = new HashSet<InetAddress>(nodeInfos.size());
        for (int p = 0; p < partitions.size(); p++) {
            int index = partitions.get(p).master();
            if (index >= 0) {
                nodes.add(nodeInfos.get(index).hostname());
            }
        }
        return nodes;
    }

