    /**
     * Get the current set of buckets known to this pool member.
     *
     * @return an atomic reference to the current Map of buckets
     */
    private AtomicReference<Map<String, Bucket>> getCurrentBuckets() {
	if (currentBuckets == null) {
	    throw new ConfigurationException("Buckets were never populated.");
	}
	return currentBuckets;
