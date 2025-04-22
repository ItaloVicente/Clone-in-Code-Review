    public MemcachedNode getAlternative(String k, Collection<MemcachedNode> notMyVbucketNodes) {
        Collection<MemcachedNode> nodes = nodesMap.values();
        nodes.removeAll(notMyVbucketNodes);
        if (nodes.isEmpty()) {
            return null;
        } else {
            return nodes.iterator().next();
        }
    }
