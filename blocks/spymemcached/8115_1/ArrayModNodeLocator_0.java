    @Override
    public void updateLocator(List<MemcachedNode> nodes, Config conf) {
        this.nodes=nodes.toArray(new MemcachedNode[nodes.size()]);
    }

    private int getServerForKey(String key) {
