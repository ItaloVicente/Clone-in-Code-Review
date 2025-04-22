    /**
     * Method returns the node that is not contained in the specified collection of the failed nodes
     * @param k the key
     * @param notMyVbucketNodes a collection of the nodes are excluded
     * @return The first MemcachedNode which meets requirements
     */
    public MemcachedNode getAlternative(String k, Collection<MemcachedNode> notMyVbucketNodes) {
        Map<String, MemcachedNode> nodesMap = new HashMap<String,MemcachedNode>(fullConfig.get().getNodesMap());
        Collection<MemcachedNode> nodes = nodesMap.values();
        nodes.removeAll(notMyVbucketNodes);
        if (nodes.isEmpty()) {
            return null;
        } else {
            return nodes.iterator().next();
        }
