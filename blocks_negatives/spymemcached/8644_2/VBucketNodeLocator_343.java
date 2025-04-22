    /**
     * {@inheritDoc}
     */
    public Collection<MemcachedNode> getAll() {
	Map<String, MemcachedNode> nodesMap = fullConfig.get().getNodesMap();
	return nodesMap.values();
