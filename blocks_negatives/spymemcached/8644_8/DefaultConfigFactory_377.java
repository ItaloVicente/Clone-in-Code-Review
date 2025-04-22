
	/* ep is for ep-engine, a.k.a. membase */
    private Config parseEpJSON(JSONObject jsonObject) throws JSONException {

        HashAlgorithm hashAlgorithm = lookupHashAlgorithm(jsonObject.getString("hashAlgorithm"));
        int replicasCount = jsonObject.getInt("numReplicas");
        if (replicasCount > VBucket.MAX_REPLICAS) {
            throw new ConfigParsingException("Expected number <= "
                    + VBucket.MAX_REPLICAS + " for replicas.");
        }
        JSONArray servers = jsonObject.getJSONArray("serverList");
        if (servers.length() <= 0) {
            throw new ConfigParsingException("Empty servers list.");
        }
        int serversCount = servers.length();
        JSONArray vbuckets = jsonObject.getJSONArray("vBucketMap");
        int vbucketsCount = vbuckets.length();
        if (vbucketsCount == 0 || (vbucketsCount & (vbucketsCount - 1)) != 0) {
            throw new ConfigParsingException(
                    "Number of buckets must be a power of two, > 0 and <= "
                            + VBucket.MAX_BUCKETS);
        }
	List<String> populateServers = populateServers(servers);
	List<VBucket> populateVbuckets = populateVbuckets(vbuckets);

        DefaultConfig config = new DefaultConfig(hashAlgorithm, serversCount, replicasCount, vbucketsCount, populateServers, populateVbuckets);

        return config;
