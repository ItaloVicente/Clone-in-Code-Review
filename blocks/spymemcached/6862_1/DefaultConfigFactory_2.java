        int serversCount = nodes.length();

	CacheConfig config = new CacheConfig(serversCount);
        populateServers(config, nodes);

	return config;
    }

    private Config parseEpJSON(JSONObject jsonObject) throws JSONException {

        HashAlgorithm hashAlgorithm = lookupHashAlgorithm(jsonObject.getString("hashAlgorithm"));
