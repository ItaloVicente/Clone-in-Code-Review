
    /**
     * {@inheritDoc}
     */
    public MemcachedNode getPrimary(String k) {
        TotalConfig totConfig = fullConfig.get();
        Config config = totConfig.getConfig();
        Map<String, MemcachedNode> nodesMap = totConfig.getNodesMap();
        int vbucket = config.getVbucketByKey(k);
        int serverNumber = config.getMaster(vbucket);
        String server = config.getServer(serverNumber);
        MemcachedNode pNode = nodesMap.get(server);
        if (pNode == null) {
            getLogger().error("The node locator does not have a primary for key %s.  Wanted vbucket %s which should be on server %s.", k, vbucket, server);
            getLogger().error("List of nodes has %s entries:", nodesMap.size());
            Set<String> keySet = nodesMap.keySet();
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                String anode = iterator.next();
                getLogger().error("MemcachedNode for %s is %s", anode, nodesMap.get(anode));
            }
            Collection<MemcachedNode> nodes = nodesMap.values();
            for (MemcachedNode node : nodes) {
                getLogger().error(node);
            }
        }
        assert (pNode != null);
        return pNode;
