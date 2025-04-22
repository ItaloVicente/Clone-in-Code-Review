    public boolean hasBucket(String bucketName) {
	boolean bucketFound = false;
	if (getCurrentBuckets().get().containsKey(bucketName)) {
		bucketFound = true;
	}
	return bucketFound;
