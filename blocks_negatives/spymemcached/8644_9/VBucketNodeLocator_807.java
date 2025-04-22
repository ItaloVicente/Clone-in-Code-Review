    private class TotalConfig {
        private Config config;
        private Map<String, MemcachedNode> nodesMap;

        public TotalConfig(Config newConfig, Map<String, MemcachedNode> newMap) {
	    config = newConfig;
	    nodesMap = Collections.unmodifiableMap(newMap);
        }

        protected Config getConfig() {
	    return config;
        }

        protected Map<String, MemcachedNode> getNodesMap() {
	    return nodesMap;
        }
