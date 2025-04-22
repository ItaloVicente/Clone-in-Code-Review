
    public void replaceBuckets(Map<String, Bucket> replacingMap) {
	HashMap<String, Bucket> swapMap = new HashMap<String, Bucket>(replacingMap); //TODO: replace this with a deep copy
	currentBuckets.set(swapMap);
    }

    public boolean hasBucket(String bucketName) {
	boolean bucketFound = false;
	if (getCurrentBuckets().get().containsKey(bucketName)) {
		bucketFound = true;
	}
	return bucketFound;
    }
