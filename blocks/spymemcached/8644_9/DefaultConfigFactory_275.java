    return ha;
  }

  private Config parseJSON(JSONObject jsonObject) throws JSONException {
    if (!jsonObject.has("vBucketServerMap")) {
      return parseCacheJSON(jsonObject);
    }
    return parseEpJSON(jsonObject.getJSONObject("vBucketServerMap"));
  }

  private Config parseCacheJSON(JSONObject jsonObject) throws JSONException {
