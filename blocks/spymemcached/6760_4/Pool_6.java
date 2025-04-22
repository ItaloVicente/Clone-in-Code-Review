    private AtomicReference<Map> getCurrentBuckets() {
	if (currentBuckets == null) {
	    throw new ConfigurationException("Buckets were never populated.");
	}
	return currentBuckets;
    }

    public Map<String, Bucket> getROBuckets() {
	return java.util.Collections.unmodifiableMap(currentBuckets.get());
    }

