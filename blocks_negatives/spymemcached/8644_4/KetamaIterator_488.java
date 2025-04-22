    public MemcachedNode next() {
	try {
	    return getNodeForKey(hashVal);
	} finally {
	    nextHash();
	}
