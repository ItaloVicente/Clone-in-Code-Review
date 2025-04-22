        if (pNode == null) {
            getLogger().error("The node locator does not have a primary for key %s.", k);
            Collection<MemcachedNode> nodes = nodesMap.values();
            getLogger().error("MemcachedNode has %s entries:", nodesMap.size());
            for (MemcachedNode node : nodes) {
                getLogger().error(node);
            }
        }
