	if (!jsonObject.has("vBucketServerMap" )) {
	    return parseCacheJSON(jsonObject);
	}
	return parseEpJSON(jsonObject.getJSONObject("vBucketServerMap"));
    }

    private Config parseCacheJSON(JSONObject jsonObject) throws JSONException {

	JSONArray nodes = jsonObject.getJSONArray("nodes");
        if (nodes.length() <= 0) {
            throw new ConfigParsingException("Empty nodes list.");
