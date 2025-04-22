    int nodeCount = nodes.size();
    int totalWeight = 0;

    if (isWeightedKetama) {
        for (MemcachedNode node : nodes) {
            totalWeight += weights.get(node.getSocketAddress());
        }
    }

