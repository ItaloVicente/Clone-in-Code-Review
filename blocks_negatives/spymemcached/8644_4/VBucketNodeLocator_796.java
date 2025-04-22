    /**
     * Returns a vbucket index for the given key
     * @param key the key
     * @return vbucket index
     */
    public int getVBucketIndex(String key) {
        Config config = fullConfig.get().getConfig();
	return config.getVbucketByKey(key);
