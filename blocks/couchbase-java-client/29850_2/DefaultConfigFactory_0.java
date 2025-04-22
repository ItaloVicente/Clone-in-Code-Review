  private void populateServersForMemcacheBucket(CacheConfig config,
    JSONArray nodes) throws JSONException {
    List<String> serverNames = new ArrayList<String>(nodes.length());
    for (int i = 0; i < nodes.length(); i++) {
      JSONObject node = nodes.getJSONObject(i);
      String[] webHostPort = node.getString("hostname").split(":");
      JSONObject portsList = node.getJSONObject("ports");
      int port = portsList.getInt("direct");
      serverNames.add(webHostPort[0] + ":" + port);
    }
    config.setServers(serverNames);
  }


  private Config parseCouchbaseBucketJSON(JSONObject source)
    throws JSONException {
    final JSONObject vBucketServerMap =
      source.getJSONObject("vBucketServerMap");

    final String algorithm = vBucketServerMap.getString("hashAlgorithm");
