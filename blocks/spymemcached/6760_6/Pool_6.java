    private AtomicReference<Map<String, Bucket>> getCurrentBuckets() {
	if (currentBuckets == null) {
	    throw new ConfigurationException("Buckets were never populated.");
	}
	return currentBuckets;
    }

    public Map<String, Bucket> getROBuckets() {
	return Collections.unmodifiableMap(currentBuckets.get());
    }

