    int nodeCount = nodes.size();

    if (isWeightedKetama && totalWeight == 0) {
        for (MemcachedNode node : nodes) {
            totalWeight += weights.get(node.getSocketAddress());
        }
    }

