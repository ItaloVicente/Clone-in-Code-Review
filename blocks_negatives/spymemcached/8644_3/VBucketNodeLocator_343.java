    public void updateLocator(final List<MemcachedNode> nodes, final Config newconf) {
	Config current = fullConfig.get().getConfig();
	ConfigDifference compareTo = current.compareTo(newconf);
	if(compareTo.isSequenceChanged() || compareTo.getVbucketsChanges() > 0) {
	    getLogger().debug("Updating configuration, received updated configuration with significant changes.");
	    fullConfig.set(new TotalConfig(newconf, fillNodesEntries(nodes)));
	} else {
	    getLogger().debug("Received updated configuration with insignificant changes.");
	}
