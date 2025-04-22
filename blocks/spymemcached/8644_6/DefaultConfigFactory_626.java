    return serverNames;
  }

  private void populateServers(CacheConfig config, JSONArray nodes)
    throws JSONException {
    List<String> serverNames = new ArrayList<String>();
    for (int i = 0; i < nodes.length(); i++) {
      JSONObject node = nodes.getJSONObject(i);
      String webHostPort = node.getString("hostname");
      String[] splitHostPort = webHostPort.split(":");
      JSONObject portsList = node.getJSONObject("ports");
      int port = portsList.getInt("direct");
      serverNames.add(splitHostPort[0] + ":" + port);
    }
    config.setServers(serverNames);
  }

  private List<VBucket> populateVbuckets(JSONArray jsonVbuckets)
    throws JSONException {
    List<VBucket> vBuckets = new ArrayList<VBucket>();
    for (int i = 0; i < jsonVbuckets.length(); i++) {
      JSONArray rows = jsonVbuckets.getJSONArray(i);
      int master = rows.getInt(0);
      int[] replicas = new int[VBucket.MAX_REPLICAS];
      for (int j = 1; j < rows.length(); j++) {
        replicas[j - 1] = rows.getInt(j);
      }
      vBuckets.add(new VBucket(master, replicas));
    }
    return vBuckets;
  }
